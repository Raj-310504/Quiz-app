package com.example.QuizApp.exception;

public class QuizAlreadyAttemptedException extends RuntimeException {
    public QuizAlreadyAttemptedException(String message) {
        super(message);
    }
}
