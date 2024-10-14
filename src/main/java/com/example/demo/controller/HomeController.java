package com.example.demo.controller;

import com.example.demo.model.ChatGptResponse;
import com.example.demo.model.FileInfo;
import com.example.demo.model.MCQuestionInfo;
import com.example.demo.model.TrueOrFalseQuestion;
import com.example.demo.service.JWTService;
import com.example.demo.service.OpenAiService;
import com.example.demo.util.Utils;
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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getDetails(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String username = jwtService.getUsernameFromToken(authorizationHeader.substring(7));
            return new ResponseEntity<>(username, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    public ResponseEntity<?> getQuestions(@RequestHeader("Authorization") String authorizationHeader ,@RequestBody FileInfo fileInfo) throws Exception {
        List<String> response = openAiService.getOpenAIResponse(fileInfo.getFile());
        List<MCQuestionInfo> questions1 = Utils.parseMCQuestion(response.get(0));
        List<TrueOrFalseQuestion> questions2 = Utils.parseTrueOrFalseQuestions(response.get(1));
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
