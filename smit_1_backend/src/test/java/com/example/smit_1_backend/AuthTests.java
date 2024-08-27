package com.example.smit_1_backend;

import com.example.smit_1_backend.dtos.CredentialsDto;
import com.example.smit_1_backend.dtos.RegisterDto;
import com.example.smit_1_backend.dtos.SmitResponse;
import com.example.smit_1_backend.dtos.UserDto;
import com.example.smit_1_backend.entities.User;
import com.example.smit_1_backend.exceptions.AppException;
import com.example.smit_1_backend.repositories.UserRepository;
import com.example.smit_1_backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthTests {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testShouldNotRegister() {
        RegisterDto registerDto = new RegisterDto(
                "firstName",
                "lastName",
                "email.example.com",
                "12345"
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        try {
            userService.register(registerDto);
        } catch (AppException appException) {
            assertEquals(
                    "User with provided email already exists!",
                    appException.getMessage(),
                    "Message should match the expected value"
            );
        }
    }

    @Test
    public void testShouldRegister() {
        RegisterDto registerDto = new RegisterDto(
                "firstName",
                "lastName",
                "email.example@com",
                "12345"
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        SmitResponse smitResponse = userService.register(registerDto);

        assertTrue(
                smitResponse.isSuccess(),
                "SmitResponse success should be true"
        );

        assertEquals(
                "User successfully registered!",
                smitResponse.getMessage(),
                "Message should match"
        );
    }

    @Test
    public void testShouldNotLogin() {
        CredentialsDto credentialsDto = new CredentialsDto(
                "email.example@com",
                "12345"
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        try {
            userService.login(credentialsDto);
        } catch (AppException appException) {
            assertEquals(
                    "Invalid email or password!",
                    appException.getMessage(),
                    "Exception message should match"
            );
        }

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(
                UUID.randomUUID(),
                "firstName",
                "lastName",
                "email.example@com",
                "67890"
        )));

        try {
            userService.login(credentialsDto);
        } catch (AppException appException) {
            assertEquals(
                    "Invalid email or password!",
                    appException.getMessage(),
                    "Exception message should match"
            );
        }
    }

    @Test
    public void testShouldLogin() {
        CredentialsDto credentialsDto = new CredentialsDto(
                "email.example@com",
                "12345"
        );

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(
                UUID.randomUUID(),
                "firstName",
                "lastName",
                "email.example@com",
                "$2a$10$X7jEngKF9S/oFVFv3iKjOumsGu3SceeMGBJvxMi3Dqs19Gj2UpMMO"
        )));

        UserDto userDto = userService.login(credentialsDto);

        assertEquals(
                credentialsDto.email(),
                userDto.getEmail(),
                "Emails must match"
        );
    }
}
