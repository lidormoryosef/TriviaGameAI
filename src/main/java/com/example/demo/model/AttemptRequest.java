package com.example.demo.model;

import lombok.Data;

@Data
public class AttemptRequest {
    private int successMCQ;
    private int totalMCQ;
    private int successTrueOrFalse;
    private int totalTrueOrFalse;
    private String[] results;

    public Attempt toAttempt(String username,String title){
        Attempt attempt = new Attempt();
        attempt.setUsername(username);
        attempt.setTitle(title);
        attempt.setSuccessMCQ(successMCQ);
        attempt.setTotalMCQ(totalMCQ);
        attempt.setSuccessTrueOrFalse(successTrueOrFalse);
        attempt.setTotalTrueOrFalse(totalTrueOrFalse);
        attempt.setResults(results);
        return attempt;
    }
}
