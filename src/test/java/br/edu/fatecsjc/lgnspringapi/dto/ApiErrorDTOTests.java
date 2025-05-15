package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ApiErrorDTOTests {
    @Test
    public void testAllArgsConstructor() {
        String message = "An error occurred";
        Instant expectedTimestamp = Instant.parse("2021-01-01T12:00:00Z");

        ApiErrorDTO error = new ApiErrorDTO(message, expectedTimestamp);

        assertEquals(message, error.getMessage(), "O message deve ser o informado");
        assertEquals(expectedTimestamp, error.getTimestamp(), "O timestamp deve ser o informado");
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        ApiErrorDTO error = new ApiErrorDTO();
        error.setMessage("NoArgs error");

        assertEquals("NoArgs error", error.getMessage(), "O message deve ser igual ao setado");
        assertNotNull(error.getTimestamp(), "O timestamp deve ter sido inicializado");
    }

    @Test
    public void testBuilderWithoutTimestamp() {
        String message = "Builder error with default timestamp";
        ApiErrorDTO error = ApiErrorDTO.builder()
                .message(message)
                .build();

        assertEquals(message, error.getMessage(), "O message construído deve ser o informado");
        assertNotNull(error.getTimestamp(), "O timestamp padrão gerado pelo builder não deve ser nulo");

        Instant now = Instant.now();
        Duration diff = Duration.between(error.getTimestamp(), now).abs();
        assertTrue(diff.getSeconds() < 1, "O timestamp deve estar próximo do instante atual");
    }

    @Test
    public void testBuilderWithExplicitTimestamp() {
        String message = "Builder error with explicit timestamp";
        Instant explicitTimestamp = Instant.parse("2022-12-31T23:59:59Z");

        ApiErrorDTO error = ApiErrorDTO.builder()
                .message(message)
                .timestamp(explicitTimestamp)
                .build();

        assertEquals(message, error.getMessage(), "O message deve ser igual ao informado");
        assertEquals(explicitTimestamp, error.getTimestamp(), "O timestamp deve ser o timestamp explícito informado");
    }

    @Test
    public void testEqualsAndHashCode() {
        String message = "Equality test message";
        Instant timestamp = Instant.parse("2023-05-05T10:15:30Z");

        ApiErrorDTO error1 = new ApiErrorDTO(message, timestamp);
        ApiErrorDTO error2 = new ApiErrorDTO(message, timestamp);

        assertEquals(error1, error2, "As instâncias devem ser iguais com os mesmos atributos");
        assertEquals(error1.hashCode(), error2.hashCode(), "Os hashcodes devem ser iguais");
    }

    @Test
    public void testToString() {
        String message = "Test toString";
        Instant timestamp = Instant.parse("2020-06-06T12:12:12Z");
        ApiErrorDTO error = new ApiErrorDTO(message, timestamp);

        String toString = error.toString();
        assertNotNull(toString, "O toString não deve ser nulo");
        assertTrue(toString.contains(message), "O toString deve conter a mensagem");
        assertTrue(toString.contains(timestamp.toString()), "O toString deve conter o timestamp");
    }
}