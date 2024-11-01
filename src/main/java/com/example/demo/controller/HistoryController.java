package com.example.demo.controller;

import com.example.demo.JWT.JWTService;
import com.example.demo.model.Attempt;
import com.example.demo.model.AttemptRequest;
import com.example.demo.model.HistoryRequest;
import com.example.demo.model.QuestionsResponse;
import com.example.demo.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        List<HistoryRequest> response = questionsService.getFromRedis(username);
        if (response == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsByTitle(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String title){
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        QuestionsResponse response = questionsService.findByUsernameAndTitle(username,title);
        if (response != null)
            return new ResponseEntity<>(response, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @RequestMapping(value = "/{title}/attempts", method = RequestMethod.GET)
    public ResponseEntity<?> getHistoryAttempts(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String title){
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
    @RequestMapping(value = "/{title}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteQuestionsByTitle(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String title){
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            questionsService.deleteQuestionsByUsernameAndTitle(username,title);
            questionsService.deleteAllAttemptByUsernameAndTitle(username,title);
            questionsService.deleteTitleFromRedis(username,title);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e){
            System.out.println("Failed to delete data for " + title);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/{title}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAttempt(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String title, @PathVariable String id){
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            questionsService.deleteAttempt(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e){
            System.out.println("Failed to delete data for " + id + " in mongodb");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
