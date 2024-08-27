package com.example.smit_1_backend.api;

import com.example.smit_1_backend.dtos.SmitResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/health_check")
public class HealthCheckController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SmitResponse checkHealth() {
        return new SmitResponse(
                true,
                HttpStatus.OK,
                "API is working!",
                null
        );
    }
}
