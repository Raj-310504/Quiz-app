package com.example.QuizApp.controller;

import com.example.QuizApp.dto.*;
import com.example.QuizApp.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    @Autowired
    private QuizService quizService;

    // start quiz
    @PostMapping("/start/{userId}")
    public ResponseEntity<QuizStartResponseDTO> startQuiz(@PathVariable Long userId) {
        return ResponseEntity.ok(quizService.startQuiz(userId));
    }

    // get random question
    @GetMapping("/{quizId}/question")
    public ResponseEntity<QuestionResponseDTO> getRandomQuestion(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getRandomQuestion(quizId));
    }

    // submit answer
    @PostMapping("/{quizId}/answer")
    public ResponseEntity<AnswerResponseDTO> submitAnswer(
            @PathVariable Long quizId,
            @Valid @RequestBody AnswerRequestDTO request) {
        return ResponseEntity.ok(quizService.submitAnswer(quizId, request));
    }

    // quiz summary
    @GetMapping("/{quizId}/summary")
    public ResponseEntity<QuizSummaryResponseDTO> getQuizSummary(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuizSummary(quizId));
    }
}
