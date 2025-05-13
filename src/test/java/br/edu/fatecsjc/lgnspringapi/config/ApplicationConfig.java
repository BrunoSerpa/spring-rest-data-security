package br.edu.fatecsjc.lgnspringapi.config;

import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationConfigTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationConfiguration authConfig;

    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationConfig = new ApplicationConfig(userRepository);
    }

    @Test
    void userDetailsService_WhenUserExists_ReturnsUserDetails() {
        String email = "test@example.com";
        User user = User.builder().email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetailsService service = applicationConfig.userDetailsService();

        assertNotNull(service.loadUserByUsername(email));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void userDetailsService_WhenUserNotFound_ThrowsException() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserDetailsService service = applicationConfig.userDetailsService();
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(email));
    }

    @Test
    void authenticationProvider_ReturnsConfiguredProvider() {
        AuthenticationProvider provider = applicationConfig.authenticationProvider();

        assertNotNull(provider);
    }

    @Test
    void authenticationManager_ReturnsConfiguredManager() throws Exception {
        AuthenticationManager expectedManager = mock(AuthenticationManager.class);
        when(authConfig.getAuthenticationManager()).thenReturn(expectedManager);

        AuthenticationManager manager = applicationConfig.authenticationManager(authConfig);

        assertNotNull(manager);
    }

    @Test
    void passwordEncoder_ReturnsBCryptEncoder() {
        PasswordEncoder encoder = applicationConfig.passwordEncoder();

        assertTrue(encoder instanceof BCryptPasswordEncoder);

        String password = "testPassword";
        String encoded = encoder.encode(password);
        assertNotNull(encoded);
        assertNotEquals(password, encoded);
        assertTrue(encoder.matches(password, encoded));
    }
}