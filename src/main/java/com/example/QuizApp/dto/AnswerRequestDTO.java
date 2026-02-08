package com.example.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDTO {
    @NotNull(message = "Question id is required")
    @Positive(message = "Question id must be positive")
    private Long questionId;

    @NotNull(message = "Selected option is required")
    @Pattern(regexp = "^[A-D]$", message = "Selected option must be A, B, C, or D")
    private String selectedOption;// A,B,C,D
}
