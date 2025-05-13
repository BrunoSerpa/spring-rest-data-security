package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationRequestDTO;
import br.edu.fatecsjc.lgnspringapi.dto.AuthenticationResponseDTO;
import br.edu.fatecsjc.lgnspringapi.dto.RegisterRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.repository.TokenRepository;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequestDTO registerRequest;
    private User user;
    private String jwtToken;
    private String refreshToken;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequestDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        jwtToken = "jwt.token.here";
        refreshToken = "refresh.token.here";
    }

    @Test
    @DisplayName("Should successfully register a new user")
    void register_Success() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn(refreshToken);
        AuthenticationResponseDTO authResponse = authenticationService.register(registerRequest);

        assertNotNull(authResponse);
        assertEquals(jwtToken, authResponse.getAccessToken());
        assertEquals(refreshToken, authResponse.getRefreshToken());

        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    @DisplayName("Should throw exception when registering with existing email")
    void register_ExistingEmail() {
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(user));

        assertThrows(ResponseStatusException.class, () -> authenticationService.register(registerRequest));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should successfully authenticate user")
    void authenticate_Success() {
        AuthenticationRequestDTO authRequest = new AuthenticationRequestDTO(
                registerRequest.getEmail(), registerRequest.getPassword());

        when(userRepository.findByEmail(authRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);
        when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);
        when(tokenRepository.findAllValidTokenByUser(user.getId())).thenReturn(new ArrayList<>());
        AuthenticationResponseDTO authResponse = authenticationService.authenticate(authRequest);

        assertNotNull(authResponse);
        assertEquals(jwtToken, authResponse.getAccessToken());
        assertEquals(refreshToken, authResponse.getRefreshToken());

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class));
        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void refreshToken_Success() throws IOException {
        String bearerToken = "Bearer " + refreshToken;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Token validToken = Token.builder()
                .tokenValue("old.token")
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        ArrayList<Token> validTokens = new ArrayList<>();
        validTokens.add(validToken);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(bearerToken);
        when(jwtService.extractUsername(refreshToken)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(jwtToken);
        when(tokenRepository.findAllValidTokenByUser(user.getId())).thenReturn(validTokens);
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener listener) {
                // Empty because it's not needed for this test mock
            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        });

        authenticationService.refreshToken(request, response);

        verify(tokenRepository).saveAll(validTokens);
        String responseContent = outputStream.toString();
        assertTrue(responseContent.contains(jwtToken));
    }

    @Test
    @DisplayName("Should throw exception when refreshing with invalid token")
    void refreshToken_InvalidToken() {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> authenticationService.refreshToken(request, response));

        verify(jwtService, never()).extractUsername(any());
    }
}