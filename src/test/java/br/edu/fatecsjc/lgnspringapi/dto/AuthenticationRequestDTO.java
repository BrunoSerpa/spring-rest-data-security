package br.edu.fatecsjc.lgnspringapi.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestDTOTest {
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidAuthenticationRequestDTO() {
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não devem existir violações para um DTO válido");

        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
    }

    @Test
    void testInvalidEmail() {
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email("invalid-email")
                .password("password")
                .build();

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Violação deve ocorrer para email malformatado");

        ConstraintViolation<AuthenticationRequestDTO> violation = violations.iterator().next();
        assertTrue(violation.getMessage().contains("Formato de email inválido"),
                "A mensagem de erro deve indicar o formato de email inválido");
    }

    @Test
    void testNullPassword() {
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email("john.doe@example.com")
                .build();

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Violação deve ocorrer para password nulo");
    }
}