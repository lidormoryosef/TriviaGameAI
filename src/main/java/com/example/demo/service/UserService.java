package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User save(User user) {
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            return userRepository.save(user);
        }catch (DataAccessException e){
            System.out.println("Failed to save data for " + user.getUsername() + "in mongodb");
            return null;
        }
    }
    public Optional<User> findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        }catch (DataAccessException e){
            System.out.println("Failed to save data for " + username + "in mongodb");
            return Optional.empty();
        }

    }
}
