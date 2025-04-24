package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordRequestDTOTest {
    @Test
    void testChangePasswordRequestDTO() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
            .currentPassword("oldPassword")
            .newPassword("newPassword")
            .confirmationPassword("newPassword")
            .build();
        assertEquals("oldPassword", dto.getCurrentPassword());
        assertEquals("newPassword", dto.getNewPassword());
        assertEquals("newPassword", dto.getConfirmationPassword());
    }
}