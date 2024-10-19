package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Data
@RedisHash("questions")
public class HistoryRequest {
    private String title;
    private int numberMCQ;
    private int numberTrueOrFalseQ;
}
