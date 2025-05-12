package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorDTOTest {
    @Test
    void testExplicitTimestamp() {
        Instant now = Instant.now();
        ApiErrorDTO dto = ApiErrorDTO.builder()
                .message("Error occurred")
                .timestamp(now)
                .build();
        assertEquals("Error occurred", dto.getMessage());
        assertEquals(now, dto.getTimestamp());
    }

    @Test
    void testDefaultTimestamp() {
        ApiErrorDTO dto = ApiErrorDTO.builder()
                .message("Default timestamp error")
                .build();
        assertNotNull(dto.getTimestamp(), "Timestamp should not be null when not provided");

        Instant now = Instant.now();
        long timeDifference = Math.abs(now.getEpochSecond() - dto.getTimestamp().getEpochSecond());
        assertTrue(timeDifference < 1, "Default timestamp should be close to the current time");
    }
}