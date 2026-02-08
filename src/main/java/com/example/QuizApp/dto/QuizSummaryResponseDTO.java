package com.example.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSummaryResponseDTO {
    private int totalQuestions;
    private int attemptedQuestions;
    private int correctCount;
    private int incorrectCount;

    private List<QuestionResultDTO> details;
}
