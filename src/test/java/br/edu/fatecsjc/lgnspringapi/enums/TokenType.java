package br.edu.fatecsjc.lgnspringapi.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTypeTest {

    @Test
    void testTokenTypeValues() {
        assertEquals("BEARER", TokenType.BEARER.name());
    }
}