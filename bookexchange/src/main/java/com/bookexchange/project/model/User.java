package com.bookexchange.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

@NotBlank(message = "Username is mandatory")
@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
 private String username;
@NotBlank(message = "Password is mandatory")
@Size(min = 6, message = "Password must be at least 6 characters long")
 private String password;

@NotBlank(message = "Role is mandatory")
    @Pattern(
            regexp = "^(USER|ADMIN)$",
            message = "Role must be either USER or ADMIN"
    )
 private String role; // e.g., USER, ADMIN

 // Getters and Setters
 public Long getId() {
     return id;
 }

 public void setId(Long id) {
     this.id = id;
 }

 public String getUsername() {
     return username;
 }

 public void setUsername(String username) {
     this.username = username;
 }

 public String getPassword() {
     return password;
 }

 public void setPassword(String password) {
     this.password = password;
 }

 public String getRole() {
     return role;
 }

 public void setRole(String role) {
     this.role = role;
 }
}