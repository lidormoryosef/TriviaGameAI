package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.JWT.JWTService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

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
        if (userService.checkPassword(user.get(),password))
            return new ResponseEntity<>(jwtService.generateToken(username), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

@GetMapping("/login-success")
public String loginSuccessPage() {
    return "<html>" +
            "<body>" +
            "<h1>Login successful!</h1>" +
            "<p>Please wait while we process your login...</p>" +
            "<script>" +
            "    window.onload = function() {" +
            "        fetch('/api/token', {" +
            "            method: 'POST'," +
            "            credentials: 'include'" +
            "        })" +
            "        .then(response => {" +
            "           if (response.ok){ " +
            "               return response.text()" +
            "           }else{" +
            "               window.location.href = '/loginPage';" +
            "               throw new Error('Login failed. Please try again.');" +
            "           }" +
            "          })" +
            "        .then(token => {" +
            "            localStorage.setItem('jwtToken', token);" +
            "            window.location.href = '/homePage';" +
            "        })" +
            "        .catch(error => {" +
            "            console.error('Error fetching token:', error);" +
            "        });" +
            "    };" +
            "</script>" +
            "</body>" +
            "</html>";
}
    @PostMapping("/token")
    public ResponseEntity<?> getToken(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String id;
        if (user.getAttribute("name") != null) {
            id = user.getAttribute("name");
            return new ResponseEntity<>(jwtService.generateToken(id), HttpStatus.OK);
        }
        else if (user.getAttribute("login") != null) {
            id = user.getAttribute("login");
            return new ResponseEntity<>(jwtService.generateToken(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


}
