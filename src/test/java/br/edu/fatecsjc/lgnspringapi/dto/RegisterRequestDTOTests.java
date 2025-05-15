package br.edu.fatecsjc.lgnspringapi.dto;

import br.edu.fatecsjc.lgnspringapi.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class RegisterRequestDTOTests {
    @Test
    void testBuilderAndGetter() {
        RegisterRequestDTO dto = RegisterRequestDTO.builder()
                .firstname("João")
                .lastname("Silva")
                .email("joao.silva@example.com")
                .password("password123")
                .role(Role.USER)
                .build();

        assertEquals("João", dto.getFirstname());
        assertEquals("Silva", dto.getLastname());
        assertEquals("joao.silva@example.com", dto.getEmail());
        assertEquals("password123", dto.getPassword());
        assertEquals(Role.USER, dto.getRole());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        assertNotNull(dto);

        assertNull(dto.getFirstname());
        assertNull(dto.getLastname());
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getRole());

        dto.setFirstname("Maria");
        dto.setLastname("Oliveira");
        dto.setEmail("maria.oliveira@example.com");
        dto.setPassword("secret");
        dto.setRole(Role.ADMIN);

        assertEquals("Maria", dto.getFirstname());
        assertEquals("Oliveira", dto.getLastname());
        assertEquals("maria.oliveira@example.com", dto.getEmail());
        assertEquals("secret", dto.getPassword());
        assertEquals(Role.ADMIN, dto.getRole());
    }

    @Test
    void testEqualsAndHashCode() {
        RegisterRequestDTO dto1 = RegisterRequestDTO.builder()
                .firstname("Ana")
                .lastname("Costa")
                .email("ana.costa@example.com")
                .password("abc123")
                .role(Role.USER)
                .build();

        RegisterRequestDTO dto2 = RegisterRequestDTO.builder()
                .firstname("Ana")
                .lastname("Costa")
                .email("ana.costa@example.com")
                .password("abc123")
                .role(Role.USER)
                .build();

        RegisterRequestDTO dto3 = RegisterRequestDTO.builder()
                .firstname("Carlos")
                .lastname("Santos")
                .email("carlos.santos@example.com")
                .password("xyz789")
                .role(Role.ADMIN)
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        RegisterRequestDTO dto = RegisterRequestDTO.builder()
                .firstname("Pedro")
                .lastname("Almeida")
                .email("pedro.almeida@example.com")
                .password("passw0rd")
                .role(Role.USER)
                .build();

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("pedro.almeida@example.com"));
    }
}