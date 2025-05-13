package br.edu.fatecsjc.lgnspringapi.config;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTests {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_whenPathContainsAuth_thenChainCalled() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/auth/login");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        verifyNoInteractions(jwtService, userDetailsService, tokenRepository);
    }

    @Test
    public void testDoFilterInternal_whenNoAuthHeader_thenChainCalled() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/api/test");
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
    }

    @Test
    public void testDoFilterInternal_whenAuthHeaderNotBearer_thenChainCalled() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/api/test");
        when(request.getHeader("Authorization")).thenReturn("Basic some_credentials");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
    }

    @Test
    public void testDoFilterInternal_whenInvalidToken_thenChainCalledWithoutAuthentication()
            throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/api/test");
        String bearerToken = "Bearer invalidToken";
        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        String token = "invalidToken";
        String userEmail = "user@example.com";
        when(jwtService.extractUsername(token)).thenReturn(userEmail);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);

        when(tokenRepository.findByTokenValue(token)).thenReturn(Optional.empty());

        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_whenValidToken_thenAuthenticationIsSet() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/api/test");
        String bearerToken = "Bearer validToken";
        when(request.getHeader("Authorization")).thenReturn(bearerToken);

        String token = "validToken";
        String userEmail = "user@example.com";
        when(jwtService.extractUsername(token)).thenReturn(userEmail);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);

        Token validToken = mock(Token.class);
        when(validToken.isExpired()).thenReturn(false);
        when(validToken.isRevoked()).thenReturn(false);

        when(tokenRepository.findByTokenValue(token))
                .thenReturn(Optional.of(validToken));

        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertTrue(
                SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);
    }
}