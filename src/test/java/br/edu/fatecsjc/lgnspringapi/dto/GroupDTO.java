package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupDTOTest {

    @Test
    void testGroupDTO() {
        GroupDTO dto = GroupDTO.builder()
            .id(1L)
            .name("Group A")
            .members(List.of())
            .build();
        assertEquals(1L, dto.getId());
        assertEquals("Group A", dto.getName());
        assertTrue(dto.getMembers().isEmpty());
    }
}