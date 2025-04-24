package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorDTOTest {

    @Test
    void testApiErrorDTO() {
        Instant now = Instant.now();
        ApiErrorDTO dto = ApiErrorDTO.builder()
            .message("Error occurred")
            .timestamp(now)
            .build();
        assertEquals("Error occurred", dto.getMessage());
        assertEquals(now, dto.getTimestamp());
    }
}