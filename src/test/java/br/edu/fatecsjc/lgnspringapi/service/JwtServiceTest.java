package br.edu.fatecsjc.lgnspringapi.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;
    private String secretKey;
    private long jwtExpiration;
    private long refreshExpiration;

    @BeforeEach
    void setUp() {
        secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        jwtExpiration = 86400000;
        refreshExpiration = 604800000;

        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", jwtExpiration);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", refreshExpiration);

        userDetails = new User("test@example.com", "password", new ArrayList<>());
    }

    @Test
    @DisplayName("Should extract username from token")
    void extractUsername_Success() {
        String token = createToken(userDetails, new HashMap<>(), jwtExpiration);

        String username = jwtService.extractUsername(token);

        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    @DisplayName("Should generate valid token")
    void generateToken_Success() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    @DisplayName("Should generate valid refresh token")
    void generateRefreshToken_Success() {
        String token = jwtService.generateRefreshToken(userDetails);

        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    @DisplayName("Should validate token successfully")
    void isTokenValid_Success() {
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }    @Test
    @DisplayName("Should handle expired tokens")
    void isTokenValid_ExpiredToken() {
        // Create a token with minimal expiration time
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis())) // Expire immediately
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        try {
            Thread.sleep(1); // Ensure token is expired
        } catch (InterruptedException e) {
            // Ignore
        }

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertFalse(isValid, "Token should be invalid because it's expired");
    }

    @Test
    @DisplayName("Should invalidate token with different username")
    void isTokenValid_WrongUsername() {
        String token = jwtService.generateToken(userDetails);
        UserDetails wrongUser = new User("wrong@example.com", "password", new ArrayList<>());

        boolean isValid = jwtService.isTokenValid(token, wrongUser);

        assertFalse(isValid);
    }

    private String createToken(UserDetails userDetails, HashMap<String, Object> extraClaims, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }
}