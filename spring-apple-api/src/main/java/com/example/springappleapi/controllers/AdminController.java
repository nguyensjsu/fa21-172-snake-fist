package com.example.springappleapi.controllers;

import com.example.springappleapi.Exceptions.CartNotFoundException;
import com.example.springappleapi.Exceptions.UserNotFoundException;
import com.example.springappleapi.messaging.Receiver;
import com.example.springappleapi.models.PaymentsCommand;
import com.example.springappleapi.models.Ticket;
import com.example.springappleapi.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import com.example.springappleapi.models.ShoppingCart;

import com.example.springappleapi.repositories.ShoppingCartRepository;
import com.example.springappleapi.repositories.UserRepository;

@Controller
public class AdminController {
    @Autowired
    Receiver rabbitMQReceiver;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository cartRepository;

    @RequestMapping("/admin/support/request")
    @GetMapping
    public String getAction( @ModelAttribute("command") PaymentsCommand command, Model model) {

        return "refundRequest" ;

    }
}
