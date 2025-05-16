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
    void testAllArgsConstructorAndDataMethods() {
        String message = "Custom error message";
        Instant timestamp = Instant.parse("2021-01-01T00:00:00Z");
        ApiErrorDTO error1 = new ApiErrorDTO(message, timestamp);

        assertEquals(message, error1.getMessage(), "Getter deve retornar a mensagem configurada");
        assertEquals(timestamp, error1.getTimestamp(), "Getter deve retornar o timestamp configurado");

        String toStringResult = error1.toString();
        assertNotNull(toStringResult, "toString não deve ser nulo");
        assertTrue(toStringResult.contains(message), "toString deve conter a mensagem");
        assertTrue(toStringResult.contains(timestamp.toString()), "toString deve conter o timestamp");

        ApiErrorDTO error2 = new ApiErrorDTO(message, timestamp);
        assertEquals(error1, error2, "Instâncias com os mesmos valores devem ser iguais");
        assertEquals(error1.hashCode(), error2.hashCode(), "HashCodes devem ser iguais para instâncias iguais");

        ApiErrorDTO error3 = new ApiErrorDTO("Outra mensagem", timestamp);
        assertNotEquals(error1, error3, "Instâncias com valores diferentes não devem ser iguais");
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ApiErrorDTO error = new ApiErrorDTO();
        String message = "Setter test";
        Instant timestamp = Instant.parse("2022-01-01T00:00:00Z");
        error.setMessage(message);
        error.setTimestamp(timestamp);

        assertEquals(message, error.getMessage(), "Getter para message deve retornar o valor setado");
        assertEquals(timestamp, error.getTimestamp(), "Getter para timestamp deve retornar o valor setado");
    }

    @Test
    void testBuilderWithoutExplicitTimestamp() {
        String message = "Builder message with default timestamp";
        ApiErrorDTO error = ApiErrorDTO.builder()
                .message(message)
                .build();

        assertEquals(message, error.getMessage(), "O message deve ser o informado via builder");
        assertNotNull(error.getTimestamp(), "Timestamp default não deve ser nulo");
        Instant now = Instant.now();
        Duration diff = Duration.between(error.getTimestamp(), now).abs();
        assertTrue(diff.getSeconds() < 1, "O timestamp default deve estar próximo do instante atual");
    }

    @Test
    void testBuilderWithExplicitTimestamp() {
        String message = "Builder message with explicit timestamp";
        Instant explicitTimestamp = Instant.parse("2022-12-31T23:59:59Z");
        ApiErrorDTO error = ApiErrorDTO.builder()
                .message(message)
                .timestamp(explicitTimestamp)
                .build();

        assertEquals(message, error.getMessage(), "O message construído deve ser o informado");
        assertEquals(explicitTimestamp, error.getTimestamp(),
                "O timestamp construído deve ser o timestamp explícito informado");
    }

    @Test
    void testHashCodeConsistency() {
        ApiErrorDTO error = ApiErrorDTO.builder()
                .message("Consistent")
                .timestamp(Instant.parse("2021-01-01T00:00:00Z"))
                .build();

        int hash1 = error.hashCode();
        int hash2 = error.hashCode();
        assertEquals(hash1, hash2, "HashCode deve ser consistente mesmo com múltiplas invocações");
    }
}