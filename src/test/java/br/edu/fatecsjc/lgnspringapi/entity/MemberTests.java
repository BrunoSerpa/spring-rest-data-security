package br.edu.fatecsjc.lgnspringapi.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class MemberTests {
        @Test
        void testBuilderAndGetters() {
                GroupEntity group = GroupEntity.builder()
                                .id(1L)
                                .name("Test Group")
                                .build();

                Member member = Member.builder()
                                .id(10L)
                                .name("John")
                                .age(25)
                                .group(group)
                                .build();

                assertEquals(10L, member.getId());
                assertEquals("John", member.getName());
                assertEquals(25, member.getAge());
                assertEquals(group, member.getGroup());
        }

        @Test
        void testNoArgsConstructorAndSetters() {
                Member member = new Member();

                assertNull(member.getId());
                assertNull(member.getName());
                assertNull(member.getAge());
                assertNull(member.getGroup());
                member.setId(20L);
                member.setName("Alice");
                member.setAge(30);
                GroupEntity group = GroupEntity.builder()
                                .id(2L)
                                .name("Group B")
                                .build();
                member.setGroup(group);

                assertEquals(20L, member.getId());
                assertEquals("Alice", member.getName());
                assertEquals(30, member.getAge());
                assertEquals(group, member.getGroup());
        }

        @Test
        void testEqualsAndHashCode() {
                GroupEntity group = GroupEntity.builder()
                                .id(1L)
                                .name("Group A")
                                .build();

                Member member1 = Member.builder()
                                .id(1L)
                                .name("John")
                                .age(25)
                                .group(group)
                                .build();

                Member member2 = Member.builder()
                                .id(1L)
                                .name("John")
                                .age(25)
                                .group(group)
                                .build();

                Member member3 = Member.builder()
                                .id(2L)
                                .name("Jane")
                                .age(30)
                                .build();
                assertEquals(member1, member2);
                assertEquals(member1.hashCode(), member2.hashCode());
                assertNotEquals(member1, member3);
        }

        @Test
        void testToString() {
                GroupEntity group = GroupEntity.builder()
                                .id(1L)
                                .name("Group A")
                                .build();

                Member member = Member.builder()
                                .id(1L)
                                .name("John")
                                .age(25)
                                .group(group)
                                .build();

                String str = member.toString();
                assertNotNull(str);
                assertTrue(str.contains("John"));
                assertTrue(str.contains("25"));
        }

        @Test
        void testJsonSerialization() throws Exception {
                GroupEntity group = GroupEntity.builder()
                                .id(1L)
                                .name("Group A")
                                .build();

                Member member = Member.builder()
                                .id(1L)
                                .name("John")
                                .age(25)
                                .group(group)
                                .build();

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(member);
                assertFalse(json.contains("group"));
                assertTrue(json.contains("\"id\":1"));
                assertTrue(json.contains("\"name\":\"John\""));
                assertTrue(json.contains("\"age\":25"));
        }
}
