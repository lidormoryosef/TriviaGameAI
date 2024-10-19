package com.example.demo.service;

import com.example.demo.OpenAI.ChatGPTRequest;
import com.example.demo.OpenAI.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiService {
    private static final String API_KEY = "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";
    private static final String url = "https://api.openai.com/v1/chat/completions";
    private static final String model = "gpt-3.5-turbo";
    private static final String forMCQ = "give me multiple-choice questions (4 options) about this text without introduction in your response  , the questions will be in this format : 1. question ? \nA. option 1 \nB. option 2 \nC. option 3 \nD. option 4 \nAnswer: B. \n\n\" : ";
    private static final String forTrueOrFalseQ = "give me true or false questions about this text without introduction in your response  , the questions will be in this format : 1. question ? \nAnswer: true or false. \n\n:";
    @Autowired
    private RestTemplate template;
    public List<String> getOpenAIResponse(String question) throws Exception {
        try{
            List<String> result = new ArrayList<>();
            ChatGPTRequest request1 =new ChatGPTRequest(model, forMCQ + question);
            ChatGPTRequest request2 =new ChatGPTRequest(model, forTrueOrFalseQ + question);
            ChatGptResponse chatGptResponse = template.postForObject(url, request1, ChatGptResponse.class);
            result.add(chatGptResponse.getChoices().get(0).getMessage().getContent());
            chatGptResponse = template.postForObject(url,request2,ChatGptResponse.class);
            result.add(chatGptResponse.getChoices().get(0).getMessage().getContent());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}