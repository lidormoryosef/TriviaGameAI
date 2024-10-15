package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.redis.Redis;
import com.example.demo.repo.QuestionsRepository;
import com.example.demo.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionsService  {
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    Redis redis;
    @Autowired
    ObjectMapper om;
    public void save(QuestionsResponse questionsResponse) {
        new Thread(() -> {
            questionsRepository.save(questionsResponse);
            System.out.println("Saved Data for " + questionsResponse.getUsername() + "in MongoDB");
            Object history = redis.get(questionsResponse.getUsername());
            if(history != null){
                try {
                    List<HistoryRequest> historyRequests = om.readValue(history.toString(), new TypeReference<List<HistoryRequest>>() {});
                    redis.del(questionsResponse.getUsername());
                    UpdateAndSaveInRedis(questionsResponse,historyRequests);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.out.println("Failed to read data for " + questionsResponse.getUsername() + "from Redis");
                }
            }else{
                List<HistoryRequest> historyRequests = new ArrayList<>();
                UpdateAndSaveInRedis(questionsResponse,historyRequests);
            }
        }).start();
    }
    private void UpdateAndSaveInRedis(QuestionsResponse questionsResponse,List<HistoryRequest> historyRequests){
        boolean notFind = true;
        for(HistoryRequest historyRequest:historyRequests){
            if (Objects.equals(historyRequest.getTitle(), questionsResponse.getTitle())){
                historyRequest.setNumberMCQ(historyRequest.getNumberMCQ() + questionsResponse.getLenMcQuestion());
                historyRequest.setNumberTrueOrFalseQ(historyRequest.getNumberTrueOrFalseQ() + questionsResponse.getLenTrueOrFalseQuestions());
                notFind = false;
                break;
            }
        }
        if (notFind){
            historyRequests.add(new HistoryRequest(questionsResponse.getTitle(),questionsResponse.getLenMcQuestion(),questionsResponse.getLenTrueOrFalseQuestions()));
        }
        try {
            redis.set(questionsResponse.getUsername(),om.writeValueAsString(historyRequests));
            System.out.println("Saved Data for " + questionsResponse.getUsername() + "in Redis");
        }catch (Exception exception){
            System.out.println("Failed to save data for " + questionsResponse.getUsername() + "in Redis");
        }
    }
    public List<HistoryRequest> getFromRedis(String username){
        Object history = redis.get(username);
        if (history != null)
            try {
                return om.readValue(history.toString(), new TypeReference<List<HistoryRequest>>() {
                });
            }catch (Exception exception) {
                return null;
            }
        return null;
    }
    public QuestionsResponse createQuestionsResponse( List<String> response, String username, FileInfo fileInfo){
        try {
            List<MCQuestion> mcQuestions = Utils.parseMCQuestion(response.get(0));
            List<TrueOrFalseQuestion> trueOrFalseQuestions = Utils.parseTrueOrFalseQuestions(response.get(1));
            return new QuestionsResponse(username,fileInfo.getTitle(),mcQuestions,mcQuestions.size(),trueOrFalseQuestions,trueOrFalseQuestions.size());
        }catch (Exception e){
            System.out.println("Error in parse" + e.toString());
            return null;
        }
    }
    public QuestionsResponse findAllByUsernameAndTitle(String username,String title){
        return createOneFile((questionsRepository.findAllByUsernameAndTitle(username,title)));
    }
    private QuestionsResponse createOneFile(List<QuestionsResponse> responses){
        QuestionsResponse questionsResponse = new QuestionsResponse();
        for(QuestionsResponse qR : responses){
            questionsResponse.getMcQuestion().addAll(qR.getMcQuestion());
            questionsResponse.getTrueOrFalseQuestions().addAll(qR.getTrueOrFalseQuestions());
        }
        questionsResponse.setLenMcQuestion(questionsResponse.getMcQuestion().size());
        questionsResponse.setLenTrueOrFalseQuestions(questionsResponse.getTrueOrFalseQuestions().size());
        return questionsResponse;
    }
}
