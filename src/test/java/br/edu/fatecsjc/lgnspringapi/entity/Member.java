package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test
    void testMemberEntityBasic() {
        Member member = Member.builder()
                .id(1L)
                .name("Bob")
                .age(30)
                .build();

        assertEquals(1L, member.getId());
        assertEquals("Bob", member.getName());
        assertEquals(30, member.getAge());
        assertNull(member.getGroup());
    }

    @Test
    void testMemberEntityWithGroup() {
        GroupEntity group = GroupEntity.builder()
                .id(2L)
                .name("Group A")
                .build();

        Member member = Member.builder()
                .id(1L)
                .name("Bob")
                .age(30)
                .group(group)
                .build();

        assertEquals(1L, member.getId());
        assertEquals("Bob", member.getName());
        assertEquals(30, member.getAge());
        assertNotNull(member.getGroup());
        assertEquals(2L, member.getGroup().getId());
        assertEquals("Group A", member.getGroup().getName());
    }

    @Test
    void testToStringMethod() {
        Member member = Member.builder()
                .id(1L)
                .name("Bob")
                .age(30)
                .build();
        String toStringOutput = member.toString();
        assertTrue(toStringOutput.contains("Bob"), "toString() deve conter o nome 'Bob'");
        assertTrue(toStringOutput.contains("30"), "toString() deve conter a idade 30");
    }
}