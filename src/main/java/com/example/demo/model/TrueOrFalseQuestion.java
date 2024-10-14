package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
@Document(collection = "TrueOrFalseQuestions")
public class TrueOrFalseQuestion {
    @Id
    private String id;
    @NotBlank
    private String question;
    @NotBlank
    @Size(max = 60)
    private String username;
    @NotBlank
    private boolean answer;

    public static final class TrueOrFalseQuestionBuilder {
        private String id;
        private String username;
        private String question;
        private boolean answer;

        private TrueOrFalseQuestionBuilder(){}

        public static TrueOrFalseQuestionBuilder aTrueOrFalseQuestion() {
            return new TrueOrFalseQuestionBuilder();

        }
        public TrueOrFalseQuestionBuilder id(String id){
            this.id = id;
            return this;
        }


        public TrueOrFalseQuestionBuilder username(String username){
            this.username = username;
            return this;
        }
        public TrueOrFalseQuestionBuilder question(String question){
            this.question = question;
            return this;
        }

        public TrueOrFalseQuestionBuilder answer(boolean answer){
            this.answer = answer;
            return this;
        }
        public TrueOrFalseQuestion build(){
            TrueOrFalseQuestion trueOrFalseQuestionBuilder = new TrueOrFalseQuestion();
            trueOrFalseQuestionBuilder.setId(id);
            trueOrFalseQuestionBuilder.setUsername(username);
            trueOrFalseQuestionBuilder.setQuestion(question);
            trueOrFalseQuestionBuilder.setAnswer(answer);
            return trueOrFalseQuestionBuilder;
        }
    }
}
