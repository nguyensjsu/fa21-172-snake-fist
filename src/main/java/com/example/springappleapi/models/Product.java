package com.example.springappleapi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Getter
    private @Id @GeneratedValue Long id;
    @Getter@Setter
    private String title;
    @Getter@Setter
    private String subtitle;
    @Getter@Setter
    private String description;
    @Getter@Setter
    private String[] images;
    @Getter@Setter
    private int inventory;
    @Getter@Setter
    private String status;
    @Getter@Setter
    private Date createdAt;
}
