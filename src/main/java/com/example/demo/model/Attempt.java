package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "attempts")
    public class Attempt {
        @Id
        private String id;
        private String username;
        private String title;
        private int successMCQ;
        private int totalMCQ;
        private int successTrueOrFalse;
        private int totalTrueOrFalse;
        private String[] results;


    }
