package com.example.smit_1_backend.dtos;

import java.util.UUID;

public record ReservedBookDto(
        UUID uuid,
        UUID bookUuid,
        UUID userUuid,
        String startDate,
        String endDate,
        Boolean isReceived,
        Boolean isReturned,
        Boolean isCancelled,
        Boolean isSent
) { }
