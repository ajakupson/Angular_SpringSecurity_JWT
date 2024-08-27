package com.example.smit_1_backend.api;

import com.example.smit_1_backend.config.UserAuthenticationProvider;
import com.example.smit_1_backend.dtos.*;
import com.example.smit_1_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/register")
    public SmitResponse register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public SmitResponse login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));

        return new SmitResponse(
                true,
                HttpStatus.OK,
                "Logged in successfully!",
                userDto
        );
    }

    @PostMapping("/remind_password")
    public SmitResponse login(@RequestBody PasswordReminderDto passwordReminderDto) {
        return userService.remindPassword(passwordReminderDto);
    }
}
