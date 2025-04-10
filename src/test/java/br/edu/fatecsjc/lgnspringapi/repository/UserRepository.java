package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
        User user = User.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .password("password")
            .role(Role.USER)
            .build();
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getFirstName());
    }
}