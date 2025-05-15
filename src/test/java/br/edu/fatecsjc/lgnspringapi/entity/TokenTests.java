package br.edu.fatecsjc.lgnspringapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.fatecsjc.lgnspringapi.enums.TokenType;

@SpringBootTest
@ActiveProfiles("test")
class TokenTests {
        @Test
        public void testBuilderAndGetters() {
                User user = User.builder()
                                .id(1L)
                                .firstName("Test User")
                                .build();

                Token token = Token.builder()
                                .id(10L)
                                .tokenValue("abc123")
                                .tokenType(TokenType.BEARER)
                                .revoked(false)
                                .expired(false)
                                .user(user)
                                .build();

                assertEquals(10L, token.getId());
                assertEquals("abc123", token.getTokenValue());
                assertEquals(TokenType.BEARER, token.getTokenType());
                assertFalse(token.isRevoked());
                assertFalse(token.isExpired());
                assertEquals(user, token.getUser());
        }

        @Test
        public void testNoArgsConstructorAndSetters() {
                Token token = new Token();

                assertNull(token.getId());
                assertNull(token.getTokenValue());
                assertEquals(TokenType.BEARER, token.getTokenType());
                assertFalse(token.isRevoked());
                assertFalse(token.isExpired());
                assertNull(token.getUser());

                token.setId(20L);
                token.setTokenValue("xyz987");
                token.setTokenType(TokenType.BEARER);
                token.setRevoked(true);
                token.setExpired(true);

                User user = User.builder()
                                .id(2L)
                                .firstName("Another User")
                                .build();
                token.setUser(user);

                assertEquals(20L, token.getId());
                assertEquals("xyz987", token.getTokenValue());
                assertEquals(TokenType.BEARER, token.getTokenType());
                assertTrue(token.isRevoked());
                assertTrue(token.isExpired());
                assertEquals(user, token.getUser());
        }

        @Test
        public void testEqualsAndHashCode() {
                User user = User.builder()
                                .id(1L)
                                .firstName("Test User")
                                .build();

                Token token1 = Token.builder()
                                .id(1L)
                                .tokenValue("token123")
                                .tokenType(TokenType.BEARER)
                                .revoked(false)
                                .expired(false)
                                .user(user)
                                .build();

                Token token2 = Token.builder()
                                .id(1L)
                                .tokenValue("token123")
                                .tokenType(TokenType.BEARER)
                                .revoked(false)
                                .expired(false)
                                .user(user)
                                .build();

                Token token3 = Token.builder()
                                .id(2L)
                                .tokenValue("token456")
                                .tokenType(TokenType.BEARER)
                                .revoked(true)
                                .expired(true)
                                .user(null)
                                .build();

                assertEquals(token1, token2);
                assertEquals(token1.hashCode(), token2.hashCode());

                assertNotEquals(token1, token3);
        }

        @Test
        public void testToString() {
                User user = User.builder()
                                .id(1L)
                                .firstName("Test User")
                                .build();

                Token token = Token.builder()
                                .id(5L)
                                .tokenValue("stringTest")
                                .tokenType(TokenType.BEARER)
                                .revoked(false)
                                .expired(false)
                                .user(user)
                                .build();

                String str = token.toString();
                assertNotNull(str);
                assertTrue(str.contains("stringTest"));
                assertTrue(str.contains("5"));
        }

        @Test
        public void testJsonSerialization() throws Exception {
                User user = User.builder()
                                .id(1L)
                                .firstName("Json User")
                                .build();

                Token token = Token.builder()
                                .id(1L)
                                .tokenValue("jsonValue")
                                .tokenType(TokenType.BEARER)
                                .revoked(false)
                                .expired(false)
                                .user(user)
                                .build();

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(token);

                assertFalse(json.contains("user"));
                assertTrue(json.contains("\"id\":1"));
                assertTrue(json.contains("\"tokenValue\":\"jsonValue\""));
                assertTrue(json.contains("\"tokenType\":\"BEARER\""));
                assertTrue(json.contains("\"revoked\":false"));
                assertTrue(json.contains("\"expired\":false"));
        }
}