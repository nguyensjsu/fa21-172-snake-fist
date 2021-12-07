package com.example.springappleapi.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.example.springappleapi.models.PaymentsCommand;
import com.example.springappleapi.models.Product;
import com.example.springappleapi.models.ShoppingCart;
import com.example.springappleapi.models.ShoppingCartItem;
import com.example.springappleapi.models.Ticket;
import com.example.springappleapi.repositories.PaymentsCommandRepository;
import com.example.springappleapi.repositories.ProductRepository;
import com.example.springappleapi.repositories.ShoppingCartItemRepository;
import com.example.springappleapi.repositories.ShoppingCartRepository;
import com.example.springappleapi.repositories.TicketRepository;
import com.example.springappleapi.repositories.UserRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.example.springappleapi.Exceptions.CartNotFoundException;
import com.example.springappleapi.Exceptions.UserNotFoundException;
import com.example.springappleapi.Exceptions.UsernameOrEmailTakenException;
import com.example.springappleapi.Exceptions.WrongUsernamePasswordException;
import com.example.springappleapi.Utils.CartUpdatePayload;
import com.example.springappleapi.Utils.SignupPayload;
import com.example.springappleapi.messaging.Producer;
import com.example.springappleapi.messaging.Receiver;
import com.example.springappleapi.models.User;
import com.example.springappleapi.payment.PaymentProcessor;


@Slf4j 
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    private ShoppingCartItemRepository itemCartRepository;
    @Autowired
    private PaymentsCommandRepository paymentsCommandRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
	Producer rabbitMQProducer;
    @Autowired
    Receiver rabbitMQReceiver;

    @Value("${cybersource.apihost}") String apiHost;
    @Value("${cybersource.merchantkeyid}") String merchantKeyId;
    @Value("${cybersource.merchantsecretkey}") String merchantsecretKey;
    @Value("${cybersource.merchantid}") String merchantId;

    @RequestMapping("/user/signup")
    @PostMapping
    User signup(@RequestBody SignupPayload payload) {
        boolean existed = userRepository.findByEmail(payload.getEmail()).isPresent();

        if (existed){
            throw new UsernameOrEmailTakenException();
        }
        else {
            User newUser = new User(
                payload.getUsername(),
                payload.getPassword(),
                payload.getEmail(),
                "",
                "",
                "USER",
                "CREATED",
                new Date()
            );
            return userRepository.save(newUser);
        }
    }

    @RequestMapping("/user/login")
    @PostMapping
    User login(@RequestBody SignupPayload payload) {
        User user = userRepository.findByEmailAndPassword(payload.getEmail(), payload.getPassword())
            .orElseThrow(() -> new WrongUsernamePasswordException());
        return user;
    }

    private ShoppingCart getActiveCart(User user){
        Optional<ShoppingCart> activeCart = cartRepository.findByUserIdAndActive(user.getId(), true);
        if (activeCart.isPresent()){
            return activeCart.get();
        }
        else {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setActive(true);
            newCart.setCreatedAt(new Date());
            newCart.setUser(user);
            newCart = cartRepository.save(newCart);
            return newCart;
        }
    }

    @RequestMapping("/user/{userId}/shoppingCart")
    @GetMapping
    ShoppingCart getCart(@PathVariable long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            return getActiveCart(user.get());
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }

    @RequestMapping("/user/{userId}/shoppingCart/{cartId}")
    @GetMapping
    ShoppingCart getCartById(@PathVariable long userId, @PathVariable long cartId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            Optional<ShoppingCart> cart = cartRepository.findById(cartId);
            if (cart.isPresent()){
                return cart.get();
            }
            else {
                throw new CartNotFoundException(cartId);
            }
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }

    @RequestMapping("/user/{userId}/orders")
    @GetMapping
    List<ShoppingCart> getOrders(@PathVariable long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            List<ShoppingCart> carts = cartRepository.findAllByUserIdAndActive(userId, false);
            return carts;
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }

    @RequestMapping("/user/{userId}/shoppingCart/modify")
    @PostMapping
    ShoppingCart modifyCart(@PathVariable long userId, @RequestBody CartUpdatePayload payload) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            ShoppingCart activeCart = getActiveCart(user.get());
            String operation = payload.getOperation();
            long productId = payload.getProductId();
            Optional<ShoppingCartItem> currentItemQuery = itemCartRepository.findByShoppingCartIdAndItemId(activeCart.getId(), productId);
            ShoppingCartItem item;


            switch(operation){
                case "ADD":
                    int addedAmount = payload.getQuantity() > 0 ? payload.getQuantity() : 1;
                    if (currentItemQuery.isPresent()){
                        item = currentItemQuery.get();
                        
                        item.setQuantity(item.getQuantity() + addedAmount);
                    }
                    else {
                        item = new ShoppingCartItem();
                        item.shoppingCart = activeCart;
                        Product p = new Product();
                        p.setId(productId);
                        item.item = p;
                        item.setQuantity(addedAmount);
                    }
                    itemCartRepository.save(item);
                    break;
                case "REMOVE":
                    int removeAmount = payload.getQuantity() > 0 ? payload.getQuantity() : 1;
                    if (currentItemQuery.isPresent()){
                        item = currentItemQuery.get();
                        
                        item.setQuantity(item.getQuantity() - removeAmount);
                        if (item.getQuantity() > 0){
                            itemCartRepository.save(item);
                        }
                        else {
                            itemCartRepository.delete(item);
                        }
                    }
                    break;
                case "DELETE":
                    if (currentItemQuery.isPresent()){
                        item = currentItemQuery.get();
                        itemCartRepository.delete(item);
                    }
                    break;
                default:
                    break;
            }
            return activeCart;
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }

    @RequestMapping("/user/{userId}/shoppingCart/pay")
    @PostMapping
    ShoppingCart pay(@PathVariable long userId, @RequestBody PaymentsCommand command) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            ShoppingCart activeCart = getActiveCart(user.get()); 
            command.cart = activeCart;     
            command = paymentsCommandRepository.save(command);       
            
            float total = 0;

            for (int i = 0; i < activeCart.items.size(); i++){
                total += activeCart.items.get(i).getQuantity() * activeCart.items.get(i).item.getPrice();
            }

            total = total * 1.1f;

            PaymentProcessor paymentProcessor = new PaymentProcessor(apiHost, merchantKeyId, merchantsecretKey, merchantId);
            String response = paymentProcessor.process(command, "" + total, userId);   

            command.setResponse(response);
            command.setStatus(response.split("\\|").length == 2 ? "SUCCESS" : "FAIL");
            if (command.getStatus().compareTo("SUCCESS") == 0){
                activeCart.setActive(false);
            }

            paymentsCommandRepository.save(command);

            return activeCart;
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }

    @PostMapping(value = "/user/{userId}/shoppingCart/{cartId}/refund")
	public String producer(@PathVariable long userId, @PathVariable long cartId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            Optional<ShoppingCart> cart = cartRepository.findById(cartId);
            if (cart.isPresent()){
                ShoppingCart order = cart.get();

                Ticket ticket = new Ticket();
                ticket.setUserId(userId);
                ticket.setCartId(cartId);
                ticket.setRequest("REFUND");
                ticket.setStatus("CREATED");

                ticket = ticketRepository.save(ticket);
                // Ticket ticket = new Ticket(1l, 2l, 4l, "REFUND", "CREATED", "can I get a refund?");
		        rabbitMQProducer.send(ticket);

                PaymentsCommand transaction = order.transactions.get(order.transactions.size() - 1);
                transaction.setStatus("REFUND_REQUESTED");

                paymentsCommandRepository.save(transaction);
		        return "{\"status\":\"ok\"}";
            }
            else {
                throw new CartNotFoundException(cartId);
            }
        }
        else {
            throw new UserNotFoundException(userId);
        }
	}

    @PostMapping(value = "/admin/shoppingCart/{cartId}/refund")
	public String refund(@PathVariable long cartId) {
        Optional<ShoppingCart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()){
            ShoppingCart order = cart.get();

            float total = 0;

            for (int i = 0; i < order.items.size(); i++){
                total += order.items.get(i).getQuantity() * order.items.get(i).item.getPrice();
            }

            total = total * 1.1f;

            PaymentProcessor paymentProcessor = new PaymentProcessor(apiHost, merchantKeyId, merchantsecretKey, merchantId);
            PaymentsCommand transaction = order.transactions.get(order.transactions.size() - 1);
            String res = paymentProcessor.refund(transaction, "" + total, order.getId()); 
            
            transaction.setStatus("REFUNDED");
            transaction.setResponse(transaction.getResponse() + "|" + res);
            paymentsCommandRepository.save(transaction);

            return "{\"status\":\"ok\"}";
        }
        else {
            throw new CartNotFoundException(cartId);
        }
	}

    @RequestMapping("/admin/ticket/requests")
    @GetMapping
    public ShoppingCart getRequestFromQueue() {
        Ticket ticket = rabbitMQReceiver.receive();
        if (ticket == null) return null;
        
        long userId = ticket.getUserId();
        long cartId = ticket.getCartId();

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            Optional<ShoppingCart> cart = cartRepository.findById(cartId);
            if (cart.isPresent()){
                return cart.get(); 
            }
            else {
                throw new CartNotFoundException(cartId);
            }
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }
}

