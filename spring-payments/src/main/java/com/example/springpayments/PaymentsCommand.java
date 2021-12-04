package com.example.springpayments;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name ="Payments")
@Data
@RequiredArgsConstructor
class PaymentsCommand {
    private @Id @GeneratedValue Long id;

    transient private String action;
    private String firstname ;
    private String lastname ;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String cardnum;
    private String cardexpmon;
    private String cardexyear;
    private String cardcvv;
    private String email;
    private String notes;

    private String cardNumber;
    private String transactionAmount;
    private String transactionCurrency;
    private String authId;
    private String authStatus;
    private String captureId;
    private String captureStatus;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
    public String getZip(){
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getCardnum() {
        return cardnum;
    }

    public String getCardexpmon() {
        return cardexpmon;
    }

    public String getCardexyear() {
        return cardexyear;
    }

    public String getCardcvv() {
        return cardcvv;
    }

    public String getEmail() {
        return email;
    }

   

    public double transactionAmount(){
        return transactionAmount;
    }
}
