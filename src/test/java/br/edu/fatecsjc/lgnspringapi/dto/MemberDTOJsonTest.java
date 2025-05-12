package br.edu.fatecsjc.lgnspringapi.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberDTOJsonTest {
    @Test
    void testJsonSerializationAllFields() throws Exception {
        MemberDTO dto = MemberDTO.builder()
                .id(1L)
                .name("Alice")
                .age(25)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        assertTrue(json.contains("\"id\":1"), "JSON deve conter o campo 'id' com valor 1");
        assertTrue(json.contains("\"name\":\"Alice\""), "JSON deve conter o campo 'name' com valor 'Alice'");
        assertTrue(json.contains("\"age\":25"), "JSON deve conter o campo 'age' com valor 25");
    }

    @Test
    void testJsonSerializationExcludesNullFields() throws Exception {
        MemberDTO dto = MemberDTO.builder()
                .name("Bob")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        assertFalse(json.contains("id"), "JSON não deve conter o campo 'id' quando este é nulo");
        assertTrue(json.contains("\"name\":\"Bob\""), "JSON deve conter o campo 'name' com valor 'Bob'");
        assertFalse(json.contains("age"), "JSON não deve conter o campo 'age' quando este é nulo");
    }
}