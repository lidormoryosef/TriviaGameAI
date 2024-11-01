package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.redis.Redis;
import com.example.demo.repo.AttemptsRepository;
import com.example.demo.repo.QuestionsRepository;
import com.example.demo.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionsService {
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    AttemptsRepository attemptsRepository;
    @Autowired
    Redis redis;
    @Autowired
    ObjectMapper om;

    public QuestionsResponse save(QuestionsResponse questionsResponse) {
        QuestionsResponse saved = null;
        try {
            saved = questionsRepository.save(questionsResponse);
            System.out.println("Saved Data for " + questionsResponse.getUsername() + "in mongodb");
        } catch (Exception exception) {
            System.out.println("Failed to save data for " + questionsResponse.getUsername() + "in mongodb");
        }
        Object history = redis.get(questionsResponse.getUsername());
        if (history != null) {
            try {
                List<HistoryRequest> historyRequests = om.readValue(history.toString(), new TypeReference<List<HistoryRequest>>() {
                });
                redis.del(questionsResponse.getUsername());
                UpdateAndSaveInRedis(questionsResponse, historyRequests);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                System.out.println("Failed to read data for " + questionsResponse.getUsername() + "from Redis");
            }
        } else {
            List<HistoryRequest> historyRequests = new ArrayList<>();
            UpdateAndSaveInRedis(questionsResponse, historyRequests);
        }
        return saved;

    }

    private void UpdateAndSaveInRedis(QuestionsResponse questionsResponse, List<HistoryRequest> historyRequests) {
        boolean notFind = true;
        for (HistoryRequest historyRequest : historyRequests) {
            if (Objects.equals(historyRequest.getTitle(), questionsResponse.getTitle())) {
                historyRequest.setNumberMCQ(historyRequest.getNumberMCQ() + questionsResponse.getLenMcQuestion());
                historyRequest.setNumberTrueOrFalseQ(historyRequest.getNumberTrueOrFalseQ() + questionsResponse.getLenTrueOrFalseQuestions());
                notFind = false;
                break;
            }
        }
        if (notFind) {
            historyRequests.add(new HistoryRequest(questionsResponse.getTitle(), questionsResponse.getLenMcQuestion(), questionsResponse.getLenTrueOrFalseQuestions()));
        }
        try {
            redis.set(questionsResponse.getUsername(), om.writeValueAsString(historyRequests));
            System.out.println("Saved Data for " + questionsResponse.getUsername() + "in Redis");
        } catch (Exception exception) {
            System.out.println("Failed to save data for " + questionsResponse.getUsername() + "in Redis");
        }
    }

    public List<HistoryRequest> getFromRedis(String username) {
        Object history = redis.get(username);
        if (history != null)
            try {
                List<HistoryRequest> historyRequests =  om.readValue(history.toString(), new TypeReference<List<HistoryRequest>>() {
                });
                if (historyRequests.isEmpty())
                    return null;
                return historyRequests;
            } catch (Exception exception) {
                return null;
            }
        return null;
    }

    public QuestionsResponse createQuestionsResponse(List<String> response, String username, FileInfo fileInfo) {
        try {
            List<MCQuestion> mcQuestions = Utils.parseMCQuestion(response.get(0));
            List<TrueOrFalseQuestion> trueOrFalseQuestions = Utils.parseTrueOrFalseQuestions(response.get(1));
            return new QuestionsResponse(username, fileInfo.getTitle(), mcQuestions, mcQuestions.size(), trueOrFalseQuestions, trueOrFalseQuestions.size());
        } catch (Exception e) {
            System.out.println("Error in parse" + e);
            return null;
        }
    }

    public QuestionsResponse findByUsernameAndTitle(String username, String title) {
        try {
            QuestionsResponse questionsResponse = questionsRepository.findByUsernameAndTitle(username, title);
            questionsResponse.setTitle(title);
            questionsResponse.setUsername(username);
            return questionsResponse;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Attempt> findAllAttemptsByUsernameAndTitle(String username, String title) {
        try {
            return attemptsRepository.findAllByUsernameAndTitle(username, title);
        } catch (Exception e) {
            System.out.println("Failed to read data for " + username + "from mongodb");
            return null;
        }
    }

    public Attempt addAttempts(AttemptRequest attemptRequest, String username, String title) {
        try {
            return attemptsRepository.save(attemptRequest.toAttempt(username, title));
        } catch (Exception exception) {
            System.out.println("Failed to save data for " + username + " in mongodb");
            return null;
        }
    }
    public void deleteQuestionsByUsernameAndTitle(String username,String title) {
        questionsRepository.deleteAllByUsernameAndTitle(username,title);
        System.out.println("Delete data for " + title + " in mongodb");
    }
    public void deleteAttempt(String id) {
        ObjectId objectId = new ObjectId(id);
        attemptsRepository.deleteById(objectId.toString());
        System.out.println("Delete data for " + id + "in mongodb");
    }
    public void deleteAllAttemptByUsernameAndTitle(String username, String title) {
        attemptsRepository.deleteAllByUsernameAndTitle(username, title);
        System.out.println("Delete all attempts for " + username + "in mongodb");
    }
    public void deleteTitleFromRedis(String username , String title) throws JsonProcessingException {
        Object history = redis.get(username);
        List<HistoryRequest> historyRequests = om.readValue(history.toString(), new TypeReference<List<HistoryRequest>>() {
        });
        redis.del(username);
        historyRequests.removeIf(request -> request.getTitle().equals(title));
        redis.set(username, om.writeValueAsString(historyRequests));
        System.out.println("Delete questions for " + username + "in redis");
    }
}
