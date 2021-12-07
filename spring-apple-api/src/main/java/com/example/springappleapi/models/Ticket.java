package com.example.springappleapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    
    @Getter@Setter
    private @Id @GeneratedValue long id;

    @Getter@Setter
    private long userId;

    @Getter@Setter
    private long cartId;

    @Getter@Setter
    private String request;

    @Getter@Setter
    private String status;

    @Getter@Setter
    @Column(length = 3000) 
    private String message;
}
