package com.example.smit_1_backend.services;

import com.example.smit_1_backend.api.AuthController;
import com.example.smit_1_backend.dtos.*;
import com.example.smit_1_backend.entities.User;
import com.example.smit_1_backend.exceptions.AppException;
import com.example.smit_1_backend.mappers.UserMapper;
import com.example.smit_1_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public SmitResponse register(RegisterDto registerDto) {
        logger.info(String.format(
                "Trying to register user. Firstname: %s, lastname: %s, email: %s",
                registerDto.firstName(),
                registerDto.lastName(),
                registerDto.email()
        ));

        Optional<User> oUser = userRepository.findByEmail(registerDto.email());

        if (oUser.isPresent()) {
            throw new AppException("User with provided email already exists!", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.registerToUser(registerDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.password())));
        User savedUser = userRepository.save(user);

        logger.info("User successfully registered!");

        return new SmitResponse(
                true,
                HttpStatus.OK,
                "User successfully registered!",
                userMapper.toUserDto(savedUser)
        );
    }

    public UserDto login(CredentialsDto credentialsDto) {
        logger.info(String.format("Trying to login. Email : %s", credentialsDto.email()));

        User user = userRepository.findByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Invalid email or password!", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            logger.info("Successfully logged in");

            return userMapper.toUserDto(user);
        }

        throw new AppException("Invalid email or password!", HttpStatus.NOT_FOUND);
    }

    public UserDto findByEmail(String email) {
        logger.info(String.format("findByEmail %s", email));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        logger.info(String.format("User found"));
        return userMapper.toUserDto(user);
    }

    public SmitResponse remindPassword(PasswordReminderDto passwordReminderDto) {
        Optional<User> oUser = userRepository.findByEmail(passwordReminderDto.email());

        if (!oUser.isPresent()) {
            throw new AppException("User with such email not found", HttpStatus.NOT_FOUND);
        }

        return new SmitResponse(
                true,
                HttpStatus.OK,
                "Instructions send by email",
                null
        );
    }
}
