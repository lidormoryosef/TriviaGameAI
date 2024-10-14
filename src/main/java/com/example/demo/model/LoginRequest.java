package com.example.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class LoginRequest {
    @Length(max = 60)
    private String username;
    @Length(max = 60)
    private String password;
}
