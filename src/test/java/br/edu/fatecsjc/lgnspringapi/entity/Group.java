package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    @Test
    void testGroupEntity() {
        GroupEntity group = GroupEntity.builder()
                .id(1L)
                .name("Group A")
                .build();

        assertEquals(1L, group.getId());
        assertEquals("Group A", group.getName());
    }

    @Test
    void testGroupEntityToString() {
        GroupEntity group = GroupEntity.builder()
                .id(2L)
                .name("Group B")
                .build();

        String toStringOutput = group.toString();
        assertTrue(toStringOutput.contains("2"), "O método toString deve conter o ID");
        assertTrue(toStringOutput.contains("Group B"), "O método toString deve conter o nome do grupo");
    }
}