package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()){
            return new ResponseEntity<>(jwtService.generateToken(username), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/welcome")
    public String welcome(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return "You must be logged in to view this page.";
        }
        String name;
        String id = "";
        if (user.getAttribute("sub") != null) {
            name = user.getAttribute("name");
            id = user.getAttribute("sub");
        }
        else if (user.getAttribute("login") != null) {
            name = user.getAttribute("login");
        } else {
            name = "Guest";
            id = "No ID";
        }

        return "Hello, " + name + "! Your ID is: " + id;
    }

}
