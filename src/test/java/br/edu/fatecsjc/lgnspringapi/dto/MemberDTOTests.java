package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberDTOTests {
    @Test
    void testMemberDTO() {
        MemberDTO dto = MemberDTO.builder()
                .id(1L)
                .name("Alice")
                .age(25)
                .build();
        assertEquals(1L, dto.getId());
        assertEquals("Alice", dto.getName());
        assertEquals(25, dto.getAge());
    }
}