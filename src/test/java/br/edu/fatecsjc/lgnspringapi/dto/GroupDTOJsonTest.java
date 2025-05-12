package br.edu.fatecsjc.lgnspringapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class GroupDTOJsonTest {
    @Test
    void testJsonSerializationWithNonNullMembers() throws Exception {
        GroupDTO dto = GroupDTO.builder()
                .id(1L)
                .name("Group A")
                .members(List.of())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"name\":\"Group A\""));
        assertTrue(json.contains("\"members\":[]"));
    }

    @Test
    void testJsonSerializationExcludesNullFields() throws Exception {
        GroupDTO dto = GroupDTO.builder()
                .id(2L)
                .name("Group B")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);
        assertTrue(json.contains("\"id\":2"));
        assertTrue(json.contains("\"name\":\"Group B\""));
        assertFalse(json.contains("members"));
    }
}