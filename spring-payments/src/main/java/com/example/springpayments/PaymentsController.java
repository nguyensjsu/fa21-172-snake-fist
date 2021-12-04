package com.example.springpayments;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.*;
import java.time.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import com.example.springcybersource.AuthRequest;
import com.example.springcybersource.CyberSourceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

import com.example.springcybersource.*;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
@Controller
@RequestMapping("/")
public class PaymentsController {

    private static boolean DEBUG = true;

    @Value("${cybersource.apihost}") String apiHost;
    @Value("${cybersource.merchantkeyid}") String merchantKeyId;
    @Value("${cybersource.merchantsecretkey}") String merchantsecretKey;
    @Value("${cybersource.merchantid}") String merchantId;

    private CyberSourceAPI api = new CyberSourceAPI();

    private static Map<String,String> months = new HashMap<>();
    static{
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("Novmeber", "11");
        months.put("December", "12");
    }

    private static Map<String,String> states = new HashMap<>();
    static {
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("ID", "Indiana");
        states.put("IA", "Iowa");
        states.put("KA", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("RI", "Rhode Island");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");

    }


    @Autowired
    private PaymentsCommandRepository repository;

    @GetMapping
    public String getAction(@ModelAttribute("command") PaymentsCommand command,
                            Model model){
        return "creditcards";
    }

    @Getter
    @Setter
    class Message{
        private String msg;
        public Message(String m){
            msg = m;
        }
    }

    class ErrorMessages{
        private ArrayList<Message> messages = new ArrayList<Message>();
        public void add(String msg){
            messages.add(new Message(msg));
        }
        public ArrayList<Message> getMessages(){
            return messages;
        }
        public void print(){
            for(Message m : messages) {
                System.out.println(m.msg);
            }
        }
    }


    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") PaymentsCommand command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {
    
        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        CyberSourceAPI.setHost( apiHost);
        CyberSourceAPI.setKey(merchantKeyId);
        CyberSourceAPI.setSecret(merchantsecretKey);
        CyberSourceAPI.setMerchant(merchantId);

        CyberSourceAPI.debugConfig();

        ErrorMessages msgs = new ErrorMessages();

        boolean hasErrors = false;
        //Check for empty fields
        if(command.getFirstname().equals("")){
            hasErrors = true;
            msgs.add("First Name Required.");
        }
        if(command.getLastname().equals("")){
            hasErrors = true;
            msgs.add("Last Name Required");
        }
        if(command.getAddress().equals("")){
            hasErrors = true;
            msgs.add("Address Required");
        }
        if(command.getCity().equals("")){
            hasErrors = true;
            msgs.add("City Required");
        }
        if(command.getState().equals("")){
            hasErrors = true;
            msgs.add("State Required");
        }
        if(command.getZip().equals("")){
            hasErrors = true;
            msgs.add("Zip Required");
        }
        if(command.getPhone().equals("")){
            hasErrors = true;
            msgs.add("Phone Required");
        }
        if(command.getCardnum().equals("")){
            hasErrors = true;
            msgs.add("Credit Card Number Required");
        }
        if(command.getCardexpmon().equals("")){
            hasErrors = true;
            msgs.add("Credit Card Expiration Month Required");
        }
        if(command.getCardexyear().equals("")){
            hasErrors = true;
            msgs.add("Credit Card Expiration Year Required");
        }
        if(command.getCardcvv().equals("")){
            hasErrors = true;
            msgs.add("Credit Card CVV Required");
        }
        if(command.getEmail().equals("")){
            hasErrors = true;
            msgs.add("Email Address Required");
        }

        //Check for invalid fields
        if(!command.getZip().matches("\\d{5}")){
            hasErrors = true;
            msgs.add("Invalid Zip");
        }
        if(command.getPhone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}")){
            hasErrors = true;
            msgs.add("Invalid Phone Number");
        }
        if(!command.getCardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")){
            hasErrors = true;
            msgs.add("Unsupported Credit Card Number");
        }

        if(!command.getCardexyear().matches("\\d{4}")){
            hasErrors = true;
            msgs.add("Invalid Credit Card Expiration Year");
        }
        if(!command.getCardcvv().matches("\\d{3}")){
            hasErrors = true;
            msgs.add("Invalid Credit Card Number");
        }
        if(months.get(command.getCardexpmon()) == null){
            hasErrors = true;
            msgs.add("Invalid Credit Card Expiration Month");
        }

        if(states.get(command.getState()) == null){
            hasErrors = true;
            msgs.add("Invalid State");
        }

      //Print Out Errors
        if(hasErrors){
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "creditcards";
        }


        /* process payment*/
        int min = 1239871;
        int max = 9999999;
        int random_int = (int)Math.floor(Math.random()*(max-min*1)*min);
        String order_num = String.valueOf(random_int);
        AuthRequest auth = new AuthRequest();
        auth.reference = order_num;
        auth.billToFirstName = command.getFirstname();
        auth.billToLastName = command.getLastname();
        auth.billToAddress = command.getAddress();
        auth.billToCity = command.getCity();
        auth.billToState = command.getState();
        auth.billToEmail = command.getEmail();
        auth.billToPhone = command.getPhone();
        auth.billToZipCode = command.getZip();
        auth.transactionAmount = "30.00";
        auth.transactionCurrency = "USD";
        auth.cardCVV = command.getCardcvv();
        auth.cardNumnber = command.getCardNumber();
        auth.cardExpMonth = command.getCardexpmon();
        auth.cardExpYear = command.getCardexyear();
        auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber);
        if(auth.cardType.equals("ERROR")){
            System.out.println("Credit Card Type Not Supported");
            model.addAttribute("message","Credit Card Type Not Supported");
                    return "creditcards";
        }
        boolean authValid = false;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " + auth.toJson());
        authResponse = api.authorize(auth);
        System.out.println("\n\nAuth Request: " + authResponse.toJson());
        authValid = true;
        if(!authResponse.status.equals("AUTHORIZED")){
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "creditcards";
        }

        boolean captureValid = false;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        if(authValid){
            capture.reference = order_num;
            capture.paymentId = authResponse.id;
            capture.transactionAmount = "30.00";
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            captureResponse = api.capture(capture);
            System.out.println("\n\nCapture Response: " + captureResponse.toJson());
            captureValid = true;
            if(!captureResponse.status.equals("PENDING")){
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "creditcards";
            }
        }

        if(authValid && captureValid){
            command.setOrderNumber(order_num);
            command.setTransactionAmount("30.00");
            command.setTransactionCurrency("USD");
            command.setAuthId(authResponse.id);
            command.setAuthStatus(authResponse.status);
            command.setCaptureId(captureResponse.id);
            command.setCaptureStatus(captureResponse.status);

            repository.save(command);

            System.out.println("Thank You for your Payment! Your Order Number is: " + order_num);
            model.addAttribute("message", "Thank You for your Payment! Your order Number is: " + order_num);

        }

        return "creditcards";
    }

}