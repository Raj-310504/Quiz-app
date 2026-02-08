package com.example.QuizApp.exception;

public class QuizNotCompletedException extends RuntimeException {
    public QuizNotCompletedException(String message) {
        super(message);
    }
}
