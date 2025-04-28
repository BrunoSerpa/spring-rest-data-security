package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    void testGroupEntity() {
        GroupEntity group = GroupEntity.builder()
            .id(1L)
            .name("Group A")
            .build();

        assertEquals("Group A", group.getName());
    }
}