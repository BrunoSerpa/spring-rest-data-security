package br.edu.fatecsjc.lgnspringapi.dto;

import br.edu.fatecsjc.lgnspringapi.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestDTOTest {

    @Test
    void testRegisterRequestDTO() {
        RegisterRequestDTO dto = RegisterRequestDTO.builder()
            .firstname("John")
            .lastname("Doe")
            .email("john.doe@example.com")
            .password("password")
            .role(Role.USER)
            .build();
        assertEquals("John", dto.getFirstname());
        assertEquals("Doe", dto.getLastname());
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
        assertEquals(Role.USER, dto.getRole());
    }
}