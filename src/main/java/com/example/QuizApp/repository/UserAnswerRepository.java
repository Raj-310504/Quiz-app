package com.example.QuizApp.repository;

import com.example.QuizApp.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    @Query(
            value = "SELECT question_id FROM user_answers WHERE quiz_id = :quizId",
            nativeQuery = true
    )
    List<Long> findAnsweredQuestionIdsByQuizId(@Param("quizId") Long quizId);

    boolean existsByQuizIdAndQuestionId(Long quizId, Long id);

    List<UserAnswer> findByQuizId(Long quizId);
}
