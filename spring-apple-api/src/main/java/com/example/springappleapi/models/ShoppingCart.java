package com.example.springappleapi.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShoppingCart {
    @Getter
    private @Id @GeneratedValue Long id;

    @Getter@Setter
    private boolean active;

    @Getter@Setter
    private Date createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "shoppingCart")
    public List<ShoppingCartItem> items;

    @OneToMany(mappedBy = "cart")
    public List<PaymentsCommand> transactions;

    @Getter@Setter
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    public User user;
}
