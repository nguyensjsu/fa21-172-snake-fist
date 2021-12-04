package com.example.springappleapi.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
public class User {
  @Getter
  private @Id @GeneratedValue Long id;
  
  @Getter@Setter
  private String username;

  @Getter@Setter
  private String password;

  @Getter@Setter
  private String email;

  @Getter@Setter
  private String firstName;

  @Getter@Setter
  private String lastName;

  @Getter@Setter
  private String role;

  @Getter@Setter
  private String status;

  @Getter@Setter
  private Date createdAt;

  @JsonManagedReference
  @OneToMany(mappedBy = "user")
  private List<ShoppingCart> shoppingCarts;

  public User(){};

  public User(String username, String password, String email, String firstName, String lastName, String role, String status, Date createdAt){
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.status = status;
    this.createdAt = createdAt;
  }
}
