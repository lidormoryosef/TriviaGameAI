package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> register(@RequestParam(required = true) String username,
                                      @RequestParam(required = true) String password){
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        if (Objects.equals(user.get().getPassword(), Utils.hashPassword(password, username))){

        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
