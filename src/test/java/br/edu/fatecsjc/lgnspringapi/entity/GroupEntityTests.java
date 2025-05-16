package br.edu.fatecsjc.lgnspringapi.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class GroupEntityTests {
        @Test
        void testBuilderAndGetters() {
                GroupEntity group = GroupEntity.builder()
                                .id(1L)
                                .name("Test Group")
                                .build();

                assertEquals(1L, group.getId());
                assertEquals("Test Group", group.getName());
        }

        @Test
        void testNoArgsConstructorAndSetters() {
                GroupEntity group = new GroupEntity();

                assertNull(group.getId());
                assertNull(group.getName());

                group.setId(2L);
                group.setName("Another Group");

                assertEquals(2L, group.getId());
                assertEquals("Another Group", group.getName());
        }

        @Test
        void testEqualsAndHashCode() {
                GroupEntity group1 = GroupEntity.builder()
                                .id(3L)
                                .name("Group")
                                .build();
                GroupEntity group2 = GroupEntity.builder()
                                .id(3L)
                                .name("Group")
                                .build();
                GroupEntity group3 = GroupEntity.builder()
                                .id(4L)
                                .name("Different Group")
                                .build();
                assertEquals(group1, group2);
                assertEquals(group1.hashCode(), group2.hashCode());
                assertNotEquals(group1, group3);
        }

        @Test
        void testToString() {
                GroupEntity group = GroupEntity.builder()
                                .id(5L)
                                .name("Group Test")
                                .build();

                String str = group.toString();
                assertNotNull(str);
                assertTrue(str.contains("Group Test"));
                assertTrue(str.contains("5"));
        }
}