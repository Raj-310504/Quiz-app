package com.example.QuizApp.repository;

import com.example.QuizApp.entity.Question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByIdNotIn(List<Long> answeredIds);

    boolean existsByQuestionTextIgnoreCase(String questionText);
}
