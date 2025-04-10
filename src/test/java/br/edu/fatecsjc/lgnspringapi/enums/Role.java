package br.edu.fatecsjc.lgnspringapi.enums;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testGetAuthoritiesForAdmin() {
        Role adminRole = Role.ADMIN;

        List<SimpleGrantedAuthority> authorities = adminRole.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("admin:create")));
        assertTrue(authorities.contains(new SimpleGrantedAuthority("admin:update")));
    }

    @Test
    void testGetAuthoritiesForUser() {
        Role userRole = Role.USER;

        List<SimpleGrantedAuthority> authorities = userRole.getAuthorities();

        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertEquals(1, authorities.size());
    }
}