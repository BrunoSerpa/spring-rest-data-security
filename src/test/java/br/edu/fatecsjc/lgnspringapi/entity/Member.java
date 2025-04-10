package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testMemberEntity() {
        Member member = Member.builder()
            .id(1L)
            .name("Bob")
            .age(30)
            .build();

        assertEquals("Bob", member.getName());
        assertEquals(30, member.getAge());
    }
}