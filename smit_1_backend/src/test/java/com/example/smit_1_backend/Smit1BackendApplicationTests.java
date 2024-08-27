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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class Smit1BackendApplicationTests {

}
