package com.example.demo.model;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import static com.example.demo.model.User.UserBuilder.aUser;
@Data
public class RegisterInfoIn {
    @Length(max = 60)
    private String firstName;
    @Length(max = 60)
    private String lastName;
    @Length(max = 60)
    private String username;
    @Length(max = 60)
    private String password;

    public User toUser() {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty())
            return null;
        return aUser().username(username).firstName(firstName).lastName(lastName).password(password)
                .build();
    }

}
