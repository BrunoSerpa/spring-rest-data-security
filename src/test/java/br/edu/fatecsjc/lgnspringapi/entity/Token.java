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

    @Test
    void testDefaultTokenType() {
        Token token = Token.builder()
                .id(2L)
                .tokenValue("defaultToken")
                .revoked(false)
                .expired(false)
                .build();

        assertEquals(TokenType.BEARER, token.getTokenType(), "O valor default de tokenType deve ser BEARER");
    }

    @Test
    void testTokenUserAssociation() {
        User user = User.builder()
                .id(10L)
                .build();

        Token token = Token.builder()
                .id(3L)
                .tokenValue("tokenWithUser")
                .revoked(false)
                .expired(false)
                .user(user)
                .build();

        assertNotNull(token.getUser(), "User deve ser associado ao token");
        assertEquals(10L, token.getUser().getId(), "O id do usuário associado deve ser 10");
    }
}
