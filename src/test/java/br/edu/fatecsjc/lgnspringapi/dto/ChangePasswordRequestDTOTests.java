package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ChangePasswordRequestDTOTests {
    @Test
    void testBuilderAndGetters() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
                .currentPassword("oldPass")
                .newPassword("newPass")
                .confirmationPassword("newPass")
                .build();

        assertEquals("oldPass", dto.getCurrentPassword());
        assertEquals("newPass", dto.getNewPassword());
        assertEquals("newPass", dto.getConfirmationPassword());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO(null, null, null);

        assertNull(dto.getCurrentPassword());
        assertNull(dto.getNewPassword());
        assertNull(dto.getConfirmationPassword());

        dto.setCurrentPassword("oldPass");
        dto.setNewPassword("newPass");
        dto.setConfirmationPassword("newPass");

        assertEquals("oldPass", dto.getCurrentPassword());
        assertEquals("newPass", dto.getNewPassword());
        assertEquals("newPass", dto.getConfirmationPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        ChangePasswordRequestDTO dto1 = ChangePasswordRequestDTO.builder()
                .currentPassword("oldPass")
                .newPassword("newPass")
                .confirmationPassword("newPass")
                .build();

        ChangePasswordRequestDTO dto2 = ChangePasswordRequestDTO.builder()
                .currentPassword("oldPass")
                .newPassword("newPass")
                .confirmationPassword("newPass")
                .build();

        ChangePasswordRequestDTO dto3 = ChangePasswordRequestDTO.builder()
                .currentPassword("diffOldPass")
                .newPassword("diffNewPass")
                .confirmationPassword("diffNewPass")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        ChangePasswordRequestDTO dto = ChangePasswordRequestDTO.builder()
                .currentPassword("oldPass")
                .newPassword("newPass")
                .confirmationPassword("newPass")
                .build();

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("oldPass"));
        assertTrue(str.contains("newPass"));
    }
}