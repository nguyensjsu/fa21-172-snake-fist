package com.example.springappleapi.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.example.springappleapi.models.Product;
import com.example.springappleapi.models.ShoppingCart;
import com.example.springappleapi.models.ShoppingCartItem;
import com.example.springappleapi.repositories.ProductRepository;
import com.example.springappleapi.repositories.ShoppingCartItemRepository;
import com.example.springappleapi.repositories.ShoppingCartRepository;
import com.example.springappleapi.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.springappleapi.Exceptions.UserNotFoundException;
import com.example.springappleapi.Exceptions.UsernameOrEmailTakenException;
import com.example.springappleapi.Exceptions.WrongUsernamePasswordException;
import com.example.springappleapi.Utils.CartUpdatePayload;
import com.example.springappleapi.Utils.SignupPayload;
import com.example.springappleapi.models.User;


@Slf4j 
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    private ShoppingCartItemRepository itemCartRepository;

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

    @RequestMapping("/user/{userId}/shoppingCart/modify")
    @PostMapping
    ShoppingCart modifyCart(@PathVariable long userId, @RequestBody CartUpdatePayload payload) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            ShoppingCart activeCart = getActiveCart(user.get());
            String operation = payload.getOperation();
            long productId = payload.getProductId();
            Optional<ShoppingCartItem> currentItemQuery = itemCartRepository.findByShoppingCartIdAndItemId(activeCart.getId(), productId);

            switch(operation){
                case "ADD":
                    ShoppingCartItem item;
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
                case "DELETE":

                default:

            }
            return activeCart;
        }
        else {
            throw new UserNotFoundException(userId);
        }
    }
}
