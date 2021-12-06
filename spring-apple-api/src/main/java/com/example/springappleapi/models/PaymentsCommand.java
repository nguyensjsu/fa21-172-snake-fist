package com.example.springappleapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class PaymentsCommand {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String address ;
    private String cardcvv ;
    private String cardexpmon ;
    private String cardexpyear ;
    private String cardnum ;
    private String city;
    private String email ;
    private String firstname ;
    private String lastname ;
    private String state ;
    private String zip ;
    @Column(length = 3000) 
    private String response;
    private String status;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    public ShoppingCart cart;
}