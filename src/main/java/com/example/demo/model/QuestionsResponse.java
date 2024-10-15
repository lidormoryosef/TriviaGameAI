package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "questions")
@Data
public class QuestionsResponse {
    @Id
    private String id;
    @NotBlank
    @Size(max = 60)
    private String username;
    private String title;
    private List<MCQuestion> mcQuestion;
    private int lenMcQuestion;
    private List<TrueOrFalseQuestion> trueOrFalseQuestions;
    private int lenTrueOrFalseQuestions;

    public QuestionsResponse(String username, String title, List<MCQuestion> mcQuestion, int lenMcQuestion, List<TrueOrFalseQuestion> trueOrFalseQuestions, int lenTrueOrFalseQuestions) {
        this.username = username;
        this.title = title;
        this.mcQuestion = mcQuestion;
        this.lenMcQuestion = lenMcQuestion;
        this.trueOrFalseQuestions = trueOrFalseQuestions;
        this.lenTrueOrFalseQuestions = lenTrueOrFalseQuestions;
    }

    public QuestionsResponse() {
        this.mcQuestion = new ArrayList<>();
        this.trueOrFalseQuestions = new ArrayList<>();
        this.lenMcQuestion = 0;
        this.lenTrueOrFalseQuestions = 0;
    }
}
