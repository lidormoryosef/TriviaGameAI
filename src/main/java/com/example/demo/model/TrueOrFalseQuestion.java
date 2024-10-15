package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class TrueOrFalseQuestion {
    @NotBlank
    private String question;
    @NotBlank
    private boolean answer;

    public static final class TrueOrFalseQuestionBuilder {
        private String question;
        private boolean answer;

        private TrueOrFalseQuestionBuilder(){}

        public static TrueOrFalseQuestionBuilder aTrueOrFalseQuestion() {
            return new TrueOrFalseQuestionBuilder();

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
            trueOrFalseQuestionBuilder.setQuestion(question);
            trueOrFalseQuestionBuilder.setAnswer(answer);
            return trueOrFalseQuestionBuilder;
        }
    }
}
