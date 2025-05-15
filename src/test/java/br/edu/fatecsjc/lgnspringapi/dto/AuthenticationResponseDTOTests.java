package br.edu.fatecsjc.lgnspringapi.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthenticationResponseDTOTests {
    @Test
    public void testAllArgsConstructor() {
        String accessToken = "token1";
        String refreshToken = "token2";

        AuthenticationResponseDTO dto = new AuthenticationResponseDTO(accessToken, refreshToken);

        assertEquals(accessToken, dto.getAccessToken(), "O accessToken deve ser o informado");
        assertEquals(refreshToken, dto.getRefreshToken(), "O refreshToken deve ser o informado");
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        AuthenticationResponseDTO dto = new AuthenticationResponseDTO();
        dto.setAccessToken("tokenA");
        dto.setRefreshToken("tokenB");

        assertEquals("tokenA", dto.getAccessToken(), "Após o setAccessToken o valor deve ser 'tokenA'");
        assertEquals("tokenB", dto.getRefreshToken(), "Após o setRefreshToken o valor deve ser 'tokenB'");
    }

    @Test
    public void testBuilder() {
        AuthenticationResponseDTO dto = AuthenticationResponseDTO.builder()
                .accessToken("access")
                .refreshToken("refresh")
                .build();

        assertEquals("access", dto.getAccessToken(), "O accessToken deve ser o informado no builder");
        assertEquals("refresh", dto.getRefreshToken(), "O refreshToken deve ser o informado no builder");
    }

    @Test
    public void testEqualsAndHashCode() {
        AuthenticationResponseDTO dto1 = AuthenticationResponseDTO.builder()
                .accessToken("token")
                .refreshToken("refresh")
                .build();
        AuthenticationResponseDTO dto2 = AuthenticationResponseDTO.builder()
                .accessToken("token")
                .refreshToken("refresh")
                .build();

        assertEquals(dto1, dto2, "Objetos com os mesmos valores devem ser iguais");
        assertEquals(dto1.hashCode(), dto2.hashCode(), "HashCodes de objetos iguais devem ser iguais");
    }

    @Test
    public void testToString() {
        AuthenticationResponseDTO dto = AuthenticationResponseDTO.builder()
                .accessToken("token")
                .refreshToken("refresh")
                .build();
        String toString = dto.toString();

        assertNotNull(toString, "toString não pode retornar null");
        assertTrue(toString.contains("token"), "toString deve conter o accessToken");
        assertTrue(toString.contains("refresh"), "toString deve conter o refreshToken");
    }

    @Test
    public void testJsonSerialization() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AuthenticationResponseDTO dto = AuthenticationResponseDTO.builder()
                .accessToken("token123")
                .refreshToken("refresh123")
                .build();

        String json = mapper.writeValueAsString(dto);
        assertTrue(json.contains("\"access_token\":\"token123\""),
                "O JSON gerado deve conter a propriedade \"access_token\" com o valor 'token123'");
        assertTrue(json.contains("\"refresh_token\":\"refresh123\""),
                "O JSON gerado deve conter a propriedade \"refresh_token\" com o valor 'refresh123'");
    }

    @Test
    public void testJsonDeserialization() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"access_token\":\"token456\",\"refresh_token\":\"refresh456\"}";

        AuthenticationResponseDTO dto = mapper.readValue(json, AuthenticationResponseDTO.class);
        assertEquals("token456", dto.getAccessToken(), "O accessToken deve ser 'token456' após a desserialização");
        assertEquals("refresh456", dto.getRefreshToken(),
                "O refreshToken deve ser 'refresh456' após a desserialização");
    }
}