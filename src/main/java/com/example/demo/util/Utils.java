package com.example.demo.util;

import com.example.demo.model.MCQuestionInfo;
import com.example.demo.model.TrueOrFalseQuestion;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<MCQuestionInfo> parseMCQuestion(String input) {
        List<MCQuestionInfo> questions = new ArrayList<>();
        String[] questionBlocks = input.split("\\n\\n");

        for (String block : questionBlocks) {
            String[] lines = block.split("\\n");

            if (lines.length < 6) continue;

            MCQuestionInfo questionInfo = new MCQuestionInfo();
            questionInfo.setQuestion(lines[0].substring(lines[0].indexOf(".") + 2));

            questionInfo.setOption1(lines[1].substring(3));
            questionInfo.setOption2(lines[2].substring(3));
            questionInfo.setOption3(lines[3].substring(3));
            questionInfo.setOption4(lines[4].substring(3));
            String answerLine = lines[5];
            char answerChar = answerLine.charAt(8);
            int answerIndex = switch (answerChar) {
                case 'A' -> 1;
                case 'B' -> 2;
                case 'C' -> 3;
                case 'D' -> 4;
                default -> 0;
            };
            questionInfo.setAnswer(answerIndex);

            questions.add(questionInfo);
        }

        return questions;
    }
    public static List<TrueOrFalseQuestion> parseTrueOrFalseQuestions(String input) {
        List<TrueOrFalseQuestion> questions = new ArrayList<>();
        String[] questionBlocks = input.split("\\n\\n");

        for (String block : questionBlocks) {
            String[] lines = block.split("\\n");

            if (lines.length < 2) continue;

            TrueOrFalseQuestion questionInfo = new TrueOrFalseQuestion();
            questionInfo.setQuestion(lines[0].substring(lines[0].indexOf(".") + 2));
            String answerLine = lines[1].toLowerCase();
            boolean answer = answerLine.contains("true");
            questionInfo.setAnswer(answer);

            questions.add(questionInfo);
        }

        return questions;
    }
}
