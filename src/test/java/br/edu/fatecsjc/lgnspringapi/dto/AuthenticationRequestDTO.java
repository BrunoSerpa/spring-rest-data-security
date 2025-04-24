package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestDTOTest {

    @Test
    void testAuthenticationRequestDTO() {
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
            .email("john.doe@example.com")
            .password("password")
            .build();
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
    }
}