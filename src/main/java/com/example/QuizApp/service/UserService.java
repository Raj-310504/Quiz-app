package com.example.QuizApp.service;

import com.example.QuizApp.dto.UserCreateRequestDTO;
import com.example.QuizApp.dto.UserResponseDTO;
import com.example.QuizApp.entity.User;
import com.example.QuizApp.exception.UserAlreadyExistsException;
import com.example.QuizApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO createUser(UserCreateRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }

        String name = request.getName();
        String email = request.getEmail();

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        String trimmedEmail = email.trim();
        if (userRepository.existsByEmail(trimmedEmail)) {
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(trimmedEmail);

        User saved = userRepository.save(user);

        return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail());
    }
}
