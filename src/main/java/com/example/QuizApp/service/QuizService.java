package com.example.QuizApp.service;

import com.example.QuizApp.dto.*;
import com.example.QuizApp.entity.Question;
import com.example.QuizApp.entity.Quiz;
import com.example.QuizApp.entity.User;
import com.example.QuizApp.entity.UserAnswer;
import com.example.QuizApp.enums.QuizStatus;
import com.example.QuizApp.exception.*;
import com.example.QuizApp.repository.QuestionRepository;
import com.example.QuizApp.repository.QuizRepository;
import com.example.QuizApp.repository.UserAnswerRepository;
import com.example.QuizApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserAnswerRepository userAnswerRepository;

    // start quiz
    public QuizStartResponseDTO startQuiz(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean alreadyAttempted = quizRepository.existsByUserId(userId);

        if(alreadyAttempted) {
            throw new QuizAlreadyAttemptedException(
                    "You have already attempted the quiz!!"
            );
        }

        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setStartTime(LocalDateTime.now());
        quiz.setStatus(QuizStatus.IN_PROGRESS);

        quizRepository.save(quiz);

        return new QuizStartResponseDTO(
                quiz.getId(),
                "Quiz started successfully"
        );
    }

    // get random question
    public QuestionResponseDTO getRandomQuestion(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        if(quiz.getStatus() == QuizStatus.COMPLETED) {
            throw new QuizCompletedException("Quiz already completed");
        }

        // already answered question IDs
        List<Long> answeredIds =
                userAnswerRepository.findAnsweredQuestionIdsByQuizId(quizId);
        System.out.println(answeredIds);

        // fetch unanswered questions
        List<Question> unansweredQuestions =
                answeredIds.isEmpty()
                        ? questionRepository.findAll()
                        : questionRepository.findByIdNotIn(answeredIds);

        if(unansweredQuestions.isEmpty()) {
            quiz.setStatus(QuizStatus.COMPLETED);
            quiz.setEndTime(LocalDateTime.now());
            quizRepository.save(quiz);

            throw new NoMoreQuestionsException(
                    "You have answered all the questions. Quiz completed!"
            );
        }

        // random pick
        Question question = unansweredQuestions
                .get(new Random().nextInt(unansweredQuestions.size()));

        return mapToQuestionResponse(question);

    }

    // submit answer
    public AnswerResponseDTO submitAnswer(Long quizId, AnswerRequestDTO request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));


        boolean alreadyAnswered =
                userAnswerRepository.existsByQuizIdAndQuestionId(
                        quizId, question.getId());

        if(alreadyAnswered) {
            throw new AlreadyAnsweredException("Question already answered");
        }

        boolean isCorrect = question.getCorrectOption()
                .equalsIgnoreCase(request.getSelectedOption());

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setQuiz(quiz);
        userAnswer.setQuestion(question);
        userAnswer.setSelectedOption(request.getSelectedOption());
        userAnswer.setCorrect(isCorrect);

        userAnswerRepository.save(userAnswer);

        return new AnswerResponseDTO(
                isCorrect,
                question.getCorrectOption(),
                isCorrect ? "Correct answer" : "Wrong answer"
        );
    }

    // quiz summary
    public QuizSummaryResponseDTO getQuizSummary(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getStatus() != QuizStatus.COMPLETED) {
            throw new QuizNotCompletedException(
                    "Please attempt all questions before viewing the summary."
            );
        }

        List<UserAnswer> answers =
                userAnswerRepository.findByQuizId(quizId);

        int totalQuestions = (int) questionRepository.count();
        int attemptedQuestions = answers.size();

        int correct = (int) answers.stream()
                .filter(UserAnswer::isCorrect)
                .count();

        int incorrect = attemptedQuestions - correct;

//        if(quiz.getStatus() != QuizStatus.COMPLETED) {
//            quiz.setStatus(QuizStatus.COMPLETED);
//            quiz.setEndTime(LocalDateTime.now());
//            quizRepository.save(quiz);
//        }

        List<QuestionResultDTO> details = answers.stream()
                .map(a -> new QuestionResultDTO(
                        a.getQuestion().getId(),
                        a.getSelectedOption(),
                        a.getQuestion().getCorrectOption()
                ))
                .toList();

        return new QuizSummaryResponseDTO(
                totalQuestions,
                attemptedQuestions,
                correct,
                incorrect,
                details
        );

    }

    // Mapper
    private QuestionResponseDTO mapToQuestionResponse(Question question) {

        List<OptionDTO> options = question.getOptions().stream()
                .map(o -> new OptionDTO(
                        o.getOptionLabel(),
                        o.getOptionText()
                ))
                .toList();

        return new QuestionResponseDTO(
                question.getId(),
                question.getQuestionText(),
                options
        );
    }
}
