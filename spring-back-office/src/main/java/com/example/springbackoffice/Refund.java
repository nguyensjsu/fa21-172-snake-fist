package com.example.springbackoffice;

import java.util.Date;

import javax.persistence.Column;
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
public class Refund {
    @Getter@Setter
    private @Id @GeneratedValue Long id;
    @Getter@Setter
    private String title;
    @Getter@Setter
    private String subtitle;
    @Getter@Setter
    private String description;
    @Getter@Setter
    private float price;
    @Getter@Setter
    private String card;
}
