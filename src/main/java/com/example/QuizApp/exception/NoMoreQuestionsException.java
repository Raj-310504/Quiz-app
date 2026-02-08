package com.example.QuizApp.exception;

public class NoMoreQuestionsException extends RuntimeException {
    public NoMoreQuestionsException(String message) {
        super(message);
    }
}
