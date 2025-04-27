package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupConverterTest {

    private GroupConverter groupConverter;

    @BeforeEach
    void setUp() {
        groupConverter = new GroupConverter(new ModelMapper());
    }

    @Test
    @DisplayName("Should convert GroupDTO to Group entity")
    void testConvertToEntity() {
        GroupDTO groupDTO = new GroupDTO(1L, "Group A", List.of());
        GroupEntity group = groupConverter.convertToEntity(groupDTO);

        assertNotNull(group);
        assertEquals("Group A", group.getName());
    }

    @Test
    @DisplayName("Should convert Group entity to GroupDTO")
    void testConvertToDto() {
        GroupEntity group = GroupEntity.builder()
                .id(1L)
                .name("Group A")
                .members(List.of(Member.builder().id(1L).name("Alice").build()))
                .build();

        GroupDTO groupDTO = groupConverter.convertToDto(group);

        assertNotNull(groupDTO);
        assertEquals("Group A", groupDTO.getName());
        assertEquals(1, groupDTO.getMembers().size());
        assertEquals("Alice", groupDTO.getMembers().get(0).getName());
    }

    @Test
    @DisplayName("Should convert list of GroupDTO to list of Group entities")
    void testConvertToEntityList() {
        GroupDTO groupDTO = new GroupDTO(1L, "Group A", List.of());
        List<GroupEntity> groups = groupConverter.convertToEntity(List.of(groupDTO));

        assertNotNull(groups);
        assertEquals(1, groups.size());
        assertEquals("Group A", groups.get(0).getName());
    }

    @Test
    @DisplayName("Should convert list of Group entities to list of GroupDTOs")
    void testConvertToDtoList() {
        GroupEntity group = GroupEntity.builder()
                .id(1L)
                .name("Group A")
                .members(List.of(Member.builder().id(1L).name("Alice").build()))
                .build();

        List<GroupDTO> groupDTOs = groupConverter.convertToDto(List.of(group));

        assertNotNull(groupDTOs);
        assertEquals(1, groupDTOs.size());
        assertEquals("Group A", groupDTOs.get(0).getName());
    }
}