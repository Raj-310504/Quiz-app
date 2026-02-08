package com.example.QuizApp.exception;

public class AlreadyAnsweredException extends RuntimeException {
    public AlreadyAnsweredException(String message) {
        super(message);
    }
}
