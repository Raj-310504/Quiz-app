package com.example.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDTO {
    @NotBlank(message = "Option label is required")
    @Pattern(regexp = "^[A-D]$", message = "Option label must be A, B, C, or D")
    private String optionLabel; //A,B,C,D

    @NotBlank(message = "Option text is required")
    @Size(max = 300, message = "Option text must be at most 300 characters")
    private String optionText;
}
