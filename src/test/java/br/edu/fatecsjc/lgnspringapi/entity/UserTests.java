package br.edu.fatecsjc.lgnspringapi.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import br.edu.fatecsjc.lgnspringapi.enums.Role;

public class UserTests {
    @Test
    public void testBuilderAndGetters() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("secret")
                .role(Role.USER)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("secret", user.getPassword());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());

        user.setId(2L);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane@example.com");
        user.setPassword("pass");
        user.setRole(Role.ADMIN);

        assertEquals(2L, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("pass", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testGetUsername() {
        User user = User.builder()
                .email("user@example.com")
                .build();
        assertEquals("user@example.com", user.getUsername());
    }

    @Test
    public void testAccountStatus() {
        User user = User.builder().build();
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testGetAuthorities() {
        User user = User.builder().role(Role.USER).build();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(Role.USER.getAuthorities(), authorities);
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("secret")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("secret")
                .role(Role.USER)
                .build();

        User user3 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .password("pass")
                .role(Role.ADMIN)
                .build();

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1, user3);
    }

    @Test
    public void testToString() {
        User user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();
        String str = user.toString();
        assertNotNull(str);
        assertTrue(str.contains("john@example.com") || str.contains("John"));
    }
}
