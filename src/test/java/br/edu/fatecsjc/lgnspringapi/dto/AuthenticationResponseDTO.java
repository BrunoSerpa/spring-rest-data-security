package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationResponseDTOTest {
    @Test
    void testAuthenticationResponseDTO() {
        AuthenticationResponseDTO dto = AuthenticationResponseDTO.builder().accessToken("accessToken")
                .refreshToken("refreshToken").build();
        assertEquals("accessToken", dto.getAccessToken());
        assertEquals("refreshToken", dto.getRefreshToken());
    }
}