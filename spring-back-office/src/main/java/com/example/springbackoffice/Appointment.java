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
public class Appointment {
    @Getter@Setter
    private @Id @GeneratedValue Long id;
    @Getter@Setter
    private String day;
    @Getter@Setter
    private int hour;
    @Getter@Setter
    private int minutes;
    @Getter@Setter
    private String location;

}
