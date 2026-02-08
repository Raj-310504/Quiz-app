package com.example.QuizApp.controller;

import com.example.QuizApp.dto.QuestionRequestDTO;
import com.example.QuizApp.dto.QuestionResponseDTO;
import com.example.QuizApp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admin/questions")
@RequiredArgsConstructor
public class AdminQuestionController {

    @Autowired
    private QuestionService questionService;

    // Add new question
    @PostMapping
    public ResponseEntity<String> addQuestion(
            @Valid @RequestBody QuestionRequestDTO request) {
        questionService.addQuestion(request);
        return ResponseEntity.ok("Question added successfully");
    }

    // Get all questions
    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }
}

