package com.sunbase.sunbase.Helpers;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HelperFunctions {
    public static String generateId(){
        int length = 20; // Adjust the length of the random string as needed
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Characters to use for generating the random string
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
