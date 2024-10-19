package com.example.demo.model;

import lombok.Data;

@Data
public class MCQuestion {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer;

    public static final class MCQuestionBuilder {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private int answer;

        private MCQuestionBuilder() {
        }

        public static MCQuestionBuilder aMCQuestionInfo() {
            return new MCQuestionBuilder();

        }

        public MCQuestionBuilder question(String question) {
            this.question = question;
            return this;
        }

        public MCQuestionBuilder option1(String option1) {
            this.option1 = option1;
            return this;
        }

        public MCQuestionBuilder option2(String option2) {
            this.option2 = option2;
            return this;
        }

        public MCQuestionBuilder option3(String option3) {
            this.option3 = option3;
            return this;
        }

        public MCQuestionBuilder option4(String option4) {
            this.option4 = option4;
            return this;
        }

        public MCQuestionBuilder answer(int answer) {
            this.answer = answer;
            return this;
        }

        public MCQuestion build() {
            MCQuestion questionInfo = new MCQuestion();
            questionInfo.setQuestion(question);
            questionInfo.setOption1(option1);
            questionInfo.setOption2(option2);
            questionInfo.setOption3(option3);
            questionInfo.setOption4(option4);
            questionInfo.setAnswer(answer);
            return questionInfo;
        }
    }
}
