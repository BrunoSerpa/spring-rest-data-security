package br.edu.fatecsjc.lgnspringapi.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    @Test
    void testPermissionValues() {
        assertEquals("admin:create", Permission.ADMIN_CREATE.getPermissionValue());
        assertEquals("admin:update", Permission.ADMIN_UPDATE.getPermissionValue());
    }
}