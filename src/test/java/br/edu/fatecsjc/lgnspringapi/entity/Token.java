package br.edu.fatecsjc.lgnspringapi.entity;

import br.edu.fatecsjc.lgnspringapi.enums.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testTokenEntity() {
        Token token = Token.builder()
            .id(1L)
            .tokenValue("sampleToken")
            .tokenType(TokenType.BEARER)
            .revoked(false)
            .expired(false)
            .build();

        assertEquals("sampleToken", token.getTokenValue());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertFalse(token.isRevoked());
        assertFalse(token.isExpired());
    }
}