package com.example.springappleapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingCartItem {
    @Getter
    private @Id @GeneratedValue Long id;

    @Getter@Setter
    private int quantity;

    @Getter@Setter
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    public ShoppingCart shoppingCart;

    @OneToOne
    @JoinColumn
    public Product item;
}
