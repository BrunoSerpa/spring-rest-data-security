package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.ChangePasswordRequestDTO;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private ChangePasswordRequestDTO validRequest;
    private Principal principal;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedCurrentPassword")
                .role(Role.USER)
                .build();

        validRequest = ChangePasswordRequestDTO.builder()
                .currentPassword("currentPassword")
                .newPassword("newPassword")
                .confirmationPassword("newPassword")
                .build();

        principal = new UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    @DisplayName("Should change password successfully")
    void changePassword_Success() {
        when(passwordEncoder.matches(validRequest.getCurrentPassword(), user.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.encode(validRequest.getNewPassword()))
                .thenReturn("encodedNewPassword");

        assertDoesNotThrow(() -> userService.changePassword(validRequest, principal));

        verify(userRepository).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    @DisplayName("Should throw exception when current password is wrong")
    void changePassword_WrongCurrentPassword() {
        when(passwordEncoder.matches(validRequest.getCurrentPassword(), user.getPassword()))
                .thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> userService.changePassword(validRequest, principal));

        assertEquals("Wrong password", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when passwords don't match")
    void changePassword_PasswordsDoNotMatch() {
        ChangePasswordRequestDTO request = ChangePasswordRequestDTO.builder()
                .currentPassword("currentPassword")
                .newPassword("newPassword")
                .confirmationPassword("differentPassword")
                .build();

        when(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()))
                .thenReturn(true);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> userService.changePassword(request, principal));

        assertEquals("Password are not the same", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}