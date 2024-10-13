package com.example.demo.model;
import org.hibernate.validator.constraints.Length;

import static com.example.demo.model.User.UserBuilder.aUser;

public class RegisterInfoIn {
    @Length(max = 60)
    private String firstName;
    @Length(max = 60)
    private String lastName;
    @Length(max = 60)
    private String username;
    @Length(max = 60)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public User toUser() {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty())
            return null;
        return aUser().username(username).firstName(firstName).lastName(lastName).password(password)
                .build();
    }

}
