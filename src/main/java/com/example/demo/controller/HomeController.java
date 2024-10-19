package com.example.demo.controller;

import com.example.demo.model.FileInfo;
import com.example.demo.model.QuestionsResponse;
import com.example.demo.JWT.JWTService;
import com.example.demo.service.OpenAiService;
import com.example.demo.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private OpenAiService openAiService;
    @Autowired
    private QuestionsService questionsService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getDetails(@RequestHeader("Authorization") String authorizationHeader) {
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if (username != null)
            return new ResponseEntity<>(username, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    public ResponseEntity<?> getQuestions(@RequestHeader("Authorization") String authorizationHeader ,@RequestBody FileInfo fileInfo) throws Exception {
        String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
        if(username == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        List<String> response = openAiService.getOpenAIResponse(fileInfo.getFile());
        if(response == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        QuestionsResponse questionsResponse = questionsService.createQuestionsResponse(response,username,fileInfo);
        questionsResponse = questionsService.save(questionsResponse);
        return new ResponseEntity<>(questionsResponse, HttpStatus.OK);
    }

}
