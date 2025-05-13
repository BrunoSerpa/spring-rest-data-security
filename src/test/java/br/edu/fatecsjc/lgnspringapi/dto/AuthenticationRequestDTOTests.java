package br.edu.fatecsjc.lgnspringapi.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestDTOTests {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testAllArgsConstructor_valid() {
        String email = "user@example.com";
        String password = "password123";
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO(email, password);

        assertEquals(email, dto.getEmail(), "O email deve ser o informado");
        assertEquals(password, dto.getPassword(), "A senha deve ser a informada");

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não devem haver violações para um DTO válido");
    }

    @Test
    public void testBuilder_valid() {
        String email = "user@example.com";
        String password = "password123";
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email(email)
                .password(password)
                .build();

        assertEquals(email, dto.getEmail(), "O email deve ser o informado via builder");
        assertEquals(password, dto.getPassword(), "A senha deve ser a informada via builder");

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não devem haver violações para um DTO válido via builder");
    }

    @Test
    public void testInvalidEmail() {
        String invalidEmail = "invalid_email";
        String password = "password123";
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO(invalidEmail, password);

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Deve haver violações para email em formato inválido");

        boolean containsExpectedMessage = violations
                .stream()
                .anyMatch(v -> "Formato de email inválido".equals(v.getMessage()));
        assertTrue(containsExpectedMessage, "A mensagem de violação deve ser 'Formato de email inválido'");
    }

    @Test
    public void testNullEmail() {
        String password = "password123";
        // Criação do DTO com email null
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email(null)
                .password(password)
                .build();

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        boolean hasEmailViolation = violations
                .stream()
                .anyMatch(v -> "email".equals(v.getPropertyPath().toString()));
        assertTrue(hasEmailViolation, "Espera-se uma violação de @NotNull para o campo email");
    }

    @Test
    public void testNullPassword() {
        String email = "user@example.com";
        // Criação do DTO com password null
        AuthenticationRequestDTO dto = AuthenticationRequestDTO.builder()
                .email(email)
                .password(null)
                .build();

        Set<ConstraintViolation<AuthenticationRequestDTO>> violations = validator.validate(dto);
        boolean hasPasswordViolation = violations
                .stream()
                .anyMatch(v -> "password".equals(v.getPropertyPath().toString()));
        assertTrue(hasPasswordViolation, "Espera-se uma violação de @NotNull para o campo password");
    }

    @Test
    public void testEqualsAndHashCode() {
        String email = "user@example.com";
        String password = "password123";
        AuthenticationRequestDTO dto1 = new AuthenticationRequestDTO(email, password);
        AuthenticationRequestDTO dto2 = AuthenticationRequestDTO.builder()
                .email(email)
                .password(password)
                .build();

        assertEquals(dto1, dto2, "DTOs com os mesmos valores devem ser iguais");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "Os hashCodes devem ser iguais para DTOs iguais");
    }

    @Test
    public void testToString() {
        String email = "user@example.com";
        String password = "password123";
        AuthenticationRequestDTO dto = new AuthenticationRequestDTO(email, password);

        String dtoString = dto.toString();
        assertNotNull(dtoString, "toString não deve retornar null");
        assertTrue(dtoString.contains(email), "O toString deve conter o email");
        assertTrue(dtoString.contains(password), "O toString deve conter a senha");
    }
}