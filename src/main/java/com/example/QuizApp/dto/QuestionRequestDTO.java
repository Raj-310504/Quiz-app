package com.example.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO {
    @NotBlank(message = "Question text is required")
    @Size(max = 200, message = "Question text must be at most 500 characters")
    private String questionText;

    @NotBlank(message = "Correct option is required")
    @Pattern(regexp = "^[A-D]$", message = "Correct option must be A, B, C, or D")
    private String correctOption;

    @NotNull(message = "Options are required")
    @Size(min = 4, max = 4, message = "Exactly 4 options are required")
    @Valid
    private List<OptionDTO> options;
}
