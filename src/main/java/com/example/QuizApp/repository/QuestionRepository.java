package com.example.QuizApp.repository;

import com.example.QuizApp.entity.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByIdNotIn(List<Long> answeredIds);

    boolean existsByQuestionTextIgnoreCase(String questionText);

    @Query(
            value = """
                    SELECT q.*
                    FROM questions q
                    WHERE NOT EXISTS (
                        SELECT 1
                        FROM user_answers ua
                        WHERE ua.quiz_id = :quizId
                          AND ua.question_id = q.id
                    )
                    ORDER BY random()
                    LIMIT 1
                    """,
            nativeQuery = true
    )
    Optional<Question> findRandomUnansweredByQuizId(@Param("quizId") Long quizId);
}
