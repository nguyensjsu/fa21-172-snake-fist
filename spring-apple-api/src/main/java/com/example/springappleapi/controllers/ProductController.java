package com.example.springappleapi.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import com.example.springappleapi.Exceptions.DeletePublishedProductException;
import com.example.springappleapi.Exceptions.ProductNotFoundException;
import com.example.springappleapi.models.Product;
import com.example.springappleapi.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
// @RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/products")
    @GetMapping
    List<Product> getProducts() {
        List<Product> products = new ArrayList<Product>();
        Iterator<Product> itr = productRepository.findAll().iterator();

        while(itr.hasNext()){
            products.add(itr.next());
        }

        return products;
    }

    @GetMapping("/products/{prodId}")
    Product getProduct(@PathVariable long prodId) {
    
        Product product = productRepository.findById(prodId)
            .orElseThrow(() -> new ProductNotFoundException(prodId));
        return product;
    }

    @DeleteMapping("/products/{prodId}")
    ResponseEntity<?> deleteProduct(@PathVariable long prodId) {
    
        Product product = productRepository.findById(prodId)
            .orElseThrow(() -> new ProductNotFoundException(prodId));

        if (product.getStatus().compareTo("PUBLISHED") != 0){
            productRepository.delete(product);
            return ResponseEntity
            .ok()
            .header("Content-Type", "application/json")
            .body("{\"Status\": \"ok\"}");
        }
        else {
            throw new DeletePublishedProductException(prodId);
        }
    }

    @PostMapping("/products")
    ResponseEntity<?> newCard(@RequestBody Product product) throws URISyntaxException {
        return ResponseEntity
            .created(new URI("placeholder"))
            .body(productRepository.save(product));
    }

    @PutMapping("/products/{prodId}")
    ResponseEntity<?> updateCard(@RequestBody Product product) throws URISyntaxException {
        return ResponseEntity
            .created(new URI("placeholder"))
            .body(productRepository.save(product));
    }
}
