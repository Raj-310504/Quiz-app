package com.example.QuizApp.repository;

import com.example.QuizApp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsByUserId(Long userId);

}
