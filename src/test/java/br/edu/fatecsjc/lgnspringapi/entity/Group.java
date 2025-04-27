package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    void testGroupEntity() {
        Member member = Member.builder()
            .id(1L)
            .name("Alice")
            .age(25)
            .build();

        GroupEntity group = GroupEntity.builder()
            .id(1L)
            .name("Group A")
            .members(List.of(member))
            .build();

        assertEquals("Group A", group.getName());
        assertEquals(1, group.getMembers().size());
        assertEquals("Alice", group.getMembers().get(0).getName());
    }
}