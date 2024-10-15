package com.example.demo.controller;

import com.example.demo.JWT.JWTService;
import com.example.demo.model.Attempt;
import com.example.demo.model.AttemptRequest;
import com.example.demo.model.QuestionsResponse;
import com.example.demo.service.OpenAiService;
import com.example.demo.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private QuestionsService questionsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getDetails(@RequestHeader("Authorization") String authorizationHeader) {
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(questionsService.getFromRedis(username), HttpStatus.OK);
    }
    @RequestMapping(value = "/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsByTitle(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String title){
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        QuestionsResponse response = questionsService.findAllByUsernameAndTitle(username,title);
        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @RequestMapping(value = "/{title}/attempts", method = RequestMethod.GET)
    public ResponseEntity<?> getHistoryAttempts(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String title){
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        List<Attempt> attempts = questionsService.findAllAttemptsByUsernameAndTitle(username,title);
        if (attempts != null)
            return new ResponseEntity<>(attempts, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @RequestMapping(value = "/{title}/addAttempt", method = RequestMethod.POST)
    public ResponseEntity<?> addAttempt(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String title, @RequestBody AttemptRequest attemptRequest){
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        Attempt attempt = questionsService.addAttempts(attemptRequest,username,title);
        if (attempt != null)
            return new ResponseEntity<>(attempt, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
