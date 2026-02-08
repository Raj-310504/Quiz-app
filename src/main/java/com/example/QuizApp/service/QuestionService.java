package com.example.QuizApp.service;

import com.example.QuizApp.dto.OptionDTO;
import com.example.QuizApp.dto.QuestionRequestDTO;
import com.example.QuizApp.dto.QuestionResponseDTO;
import com.example.QuizApp.entity.Option;
import com.example.QuizApp.entity.Question;
import com.example.QuizApp.exception.QuestionAlreadyExistsException;
import com.example.QuizApp.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void addQuestion(QuestionRequestDTO request) {
        String questionText = request.getQuestionText();
        if (questionText != null
                && questionRepository.existsByQuestionTextIgnoreCase(questionText)) {
            throw new QuestionAlreadyExistsException("Question already exists");
        }

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setCorrectOption(request.getCorrectOption());

        List<Option> options = request.getOptions().stream()
                .map(dto -> {
                    Option option = new Option();
                    option.setOptionLabel(dto.getOptionLabel());
                    option.setOptionText(dto.getOptionText());
                    option.setQuestion(question);
                    return option;
                })
                .toList();

        question.setOptions(options);

        questionRepository.save(question);
    }

    // get all questions
    public List<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // mapper
    private QuestionResponseDTO mapToResponse(Question question) {

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
