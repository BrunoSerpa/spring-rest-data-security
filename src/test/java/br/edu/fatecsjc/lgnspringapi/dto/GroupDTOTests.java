package br.edu.fatecsjc.lgnspringapi.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GroupDTOTests {
    @Test
    public void testBuilderAndGetters() {
        MemberDTO member = MemberDTO.builder()
                .id(100L)
                .name("Test Member")
                .build();
        List<MemberDTO> members = List.of(member);

        GroupDTO group = GroupDTO.builder()
                .id(1L)
                .name("Group A")
                .members(members)
                .build();

        assertEquals(1L, group.getId());
        assertEquals("Group A", group.getName());
        assertEquals(members, group.getMembers());
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        GroupDTO group = new GroupDTO();
        assertNull(group.getId());
        assertNull(group.getName());
        assertNull(group.getMembers());

        group.setId(2L);
        group.setName("Group B");
        MemberDTO member = MemberDTO.builder()
                .id(101L)
                .name("Member B")
                .build();
        group.setMembers(List.of(member));

        assertEquals(2L, group.getId());
        assertEquals("Group B", group.getName());
        assertNotNull(group.getMembers());
        assertFalse(group.getMembers().isEmpty());
    }

    @Test
    public void testEqualsAndHashCode() {
        MemberDTO member = MemberDTO.builder()
                .id(102L)
                .name("Member C")
                .build();
        List<MemberDTO> members = List.of(member);

        GroupDTO group1 = GroupDTO.builder()
                .id(3L)
                .name("Group C")
                .members(members)
                .build();
        GroupDTO group2 = GroupDTO.builder()
                .id(3L)
                .name("Group C")
                .members(members)
                .build();
        GroupDTO group3 = GroupDTO.builder()
                .id(4L)
                .name("Group D")
                .members(null)
                .build();

        assertEquals(group1, group2);
        assertEquals(group1.hashCode(), group2.hashCode());
        assertNotEquals(group1, group3);
    }

    @Test
    public void testToString() {
        GroupDTO group = GroupDTO.builder()
                .id(5L)
                .name("Group E")
                .build();

        String str = group.toString();
        assertNotNull(str);
        assertTrue(str.contains("Group E"));
        assertTrue(str.contains("5"));
    }

    @Test
    public void testJsonInclude() throws Exception {
        GroupDTO group = GroupDTO.builder()
                .name("Group F")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(group);

        assertTrue(json.contains("\"name\":\"Group F\""));
        assertFalse(json.contains("id"));
        assertFalse(json.contains("members"));
    }
}