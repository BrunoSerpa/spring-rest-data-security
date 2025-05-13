package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {
    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    private Token token;
    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = "valid.jwt.token";
        token = Token.builder()
                .tokenValue(jwt)
                .expired(false)
                .revoked(false)
                .build();
    }

    @Test
    @DisplayName("Should revoke token on logout")
    void logout_Success() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepository.findByTokenValue(jwt)).thenReturn(Optional.of(token));

        logoutService.logout(request, response, authentication);

        verify(tokenRepository).save(token);
    }

    @Test
    @DisplayName("Should handle logout without Authorization header")
    void logout_NoAuthHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByTokenValue(any());
        verify(tokenRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should handle logout with invalid Authorization header format")
    void logout_InvalidAuthHeader() {
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat");

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).findByTokenValue(any());
        verify(tokenRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should handle logout with non-existent token")
    void logout_TokenNotFound() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(tokenRepository.findByTokenValue(jwt)).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenRepository, never()).save(any());
    }
}