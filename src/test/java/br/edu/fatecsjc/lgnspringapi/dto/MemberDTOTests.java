package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MemberDTOTests {
    @Test
    void testBuilderAndGetters() {
        MemberDTO member = MemberDTO.builder()
                .id(1L)
                .name("John Doe")
                .age(30)
                .build();
        assertEquals(1L, member.getId());
        assertEquals("John Doe", member.getName());
        assertEquals(30, member.getAge());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        MemberDTO member = new MemberDTO();

        assertNull(member.getId());
        assertNull(member.getName());
        assertNull(member.getAge());

        member.setId(2L);
        member.setName("Jane Doe");
        member.setAge(25);

        assertEquals(2L, member.getId());
        assertEquals("Jane Doe", member.getName());
        assertEquals(25, member.getAge());
    }

    @Test
    void testEqualsAndHashCode() {
        MemberDTO member1 = MemberDTO.builder()
                .id(3L)
                .name("Alice")
                .age(40)
                .build();

        MemberDTO member2 = MemberDTO.builder()
                .id(3L)
                .name("Alice")
                .age(40)
                .build();

        MemberDTO member3 = MemberDTO.builder()
                .id(4L)
                .name("Bob")
                .age(35)
                .build();

        assertEquals(member1, member2);
        assertEquals(member1.hashCode(), member2.hashCode());

        assertNotEquals(member1, member3);
    }

    @Test
    void testToString() {
        MemberDTO member = MemberDTO.builder()
                .id(5L)
                .name("Charlie")
                .age(50)
                .build();

        String str = member.toString();
        assertNotNull(str);
        assertTrue(str.contains("Charlie"));
        assertTrue(str.contains("50"));
    }

    @Test
    void testJsonInclude() throws Exception {
        MemberDTO member = MemberDTO.builder()
                .name("Lucy")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(member);

        assertTrue(json.contains("\"name\":\"Lucy\""));
        assertFalse(json.contains("id"));
        assertFalse(json.contains("age"));
    }
}