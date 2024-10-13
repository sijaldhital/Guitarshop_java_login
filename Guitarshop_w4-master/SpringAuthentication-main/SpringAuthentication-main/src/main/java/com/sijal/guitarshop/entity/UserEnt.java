package com.sijal.guitarshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;


@Entity
public class UserEnt {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @NotNull
    private String username;  // Renamed to match form field
    private String password;
    private String email;

    // Getter and Setter for username
    public String getUsername() {  // Renamed getter
        return username;
    }

    public void setUsername(String username) {  // Renamed setter
        this.username = username;
    }

    // Other getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
