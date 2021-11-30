package com.example.springappleapi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
class User {
  @Getter
  private @Id @GeneratedValue Long id;
  @Getter@Setter
  private Date username;
  @Getter@Setter
  private Date password;
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
}
