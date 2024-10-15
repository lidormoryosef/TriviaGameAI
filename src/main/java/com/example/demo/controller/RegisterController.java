package com.example.demo.controller;

import com.example.demo.model.RegisterInfoIn;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    UserService userService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> register( @RequestBody RegisterInfoIn registerInfoIn)
    {
        String username = registerInfoIn.getUsername();
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        User userInfo = registerInfoIn.toUser();
        if (userInfo == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        User user1 = userService.save(userInfo);
        if (user1 == null)
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
