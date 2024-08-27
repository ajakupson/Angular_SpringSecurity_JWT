package com.example.smit_1_backend.dtos;

import java.util.UUID;

public record BookDto(
        UUID uuid,
        String title,
        String author
) {
}
