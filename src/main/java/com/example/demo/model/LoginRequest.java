package com.example.demo.model;

import org.hibernate.validator.constraints.Length;

public class LoginRequest {
    @Length(max = 60)
    private String username;
    @Length(max = 60)
    private String password;

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
}
