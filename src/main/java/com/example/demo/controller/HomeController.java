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

import java.util.ArrayList;
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
        //List<String> response = openAiService.getOpenAIResponse(fileInfo.getFile());
        List<String> response = new ArrayList<>();
        response.add(mcq);
        response.add(torf);
//        if(response == null)
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        QuestionsResponse questionsResponse = questionsService.createQuestionsResponse(response,username,fileInfo);
        //questionsService.save(questionsResponse);
        return new ResponseEntity<>(questionsResponse, HttpStatus.OK);
    }
    String mcq = "1. When did the Yom Kippur War take place?\n" +
            "A. 1948\n" +
            "B. 1973\n" +
            "C. 1982\n" +
            "D. 1991\n" +
            "Answer: B\n" +
            "\n" +
            "2. Which territories were the main focus of the Yom Kippur War?\n" +
            "A. West Bank\n" +
            "B. Gaza Strip\n" +
            "C. Sinai Peninsula and Golan Heights\n" +
            "D. Negev Desert\n" +
            "Answer: C\n" +
            "\n" +
            "3. What triggered the start of the Yom Kippur War?\n" +
            "A. Christmas Day\n" +
            "B. Hanukkah\n" +
            "C. Yom Kippur\n" +
            "D. Ramadan\n" +
            "Answer: C\n" +
            "\n" +
            "4. What was the outcome of the Yom Kippur War between Israel and Arab states?\n" +
            "A. Arab states achieved all of their objectives\n" +
            "B. Israel achieved all of its objectives\n" +
            "C. The war ended in a stalemate\n" +
            "D. Both sides experienced significant losses\n" +
            "Answer: C";
    String torf = "1. The Yom Kippur War was fought between Israel and a coalition of Arab states led by Egypt and Syria.\n" +
            "Answer: True. \n" +
            "\n" +
            "2. The war took place in territories occupied by Israel in 1967, such as the Sinai Peninsula and Golan Heights.\n" +
            "Answer: True. \n" +
            "\n" +
            "3. The United States and Soviet Union engaged in resupply efforts for their respective allies during the war.\n" +
            "Answer: True. \n" +
            "\n" +
            "4. Israeli forces crossed the Suez Canal during the Yom Kippur War.\n" +
            "Answer: True. \n" +
            "\n" +
            "5. Israel encircled the Egyptian Third Army and Suez City during the war.\n" +
            "Answer: True.";
}
