package br.edu.fatecsjc.lgnspringapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationResponseDTOJsonTest {
    @Test
    void testJsonSerializationAndDeserialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        AuthenticationResponseDTO dto = AuthenticationResponseDTO.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"access_token\":\"accessToken\""),
                "O JSON deve conter a chave 'access_token' com o valor correto.");
        assertTrue(json.contains("\"refresh_token\":\"refreshToken\""),
                "O JSON deve conter a chave 'refresh_token' com o valor correto.");

        AuthenticationResponseDTO deserialized = objectMapper.readValue(json, AuthenticationResponseDTO.class);
        assertEquals("accessToken", deserialized.getAccessToken(),
                "O accessToken deve ser restaurado corretamente após deserializar o JSON.");
        assertEquals("refreshToken", deserialized.getRefreshToken(),
                "O refreshToken deve ser restaurado corretamente após deserializar o JSON.");
    }
}