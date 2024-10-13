package com.example.demo.util;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {
    public static String hashPassword(String password , String username) {
        return BCrypt.hashpw(password, username);
    }

}
