package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
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
        assertNull(group.getId(), "ID deve ser nulo ao converter DTO para Entity");
    }

    @Test
    @DisplayName("Should convert Group entity to GroupDTO")
    void testConvertToDto() {
        GroupEntity group = GroupEntity.builder()
                .id(1L)
                .name("Group A")
                .build();

        GroupDTO groupDTO = groupConverter.convertToDto(group);

        assertNotNull(groupDTO);
        assertEquals(1L, groupDTO.getId());
        assertEquals("Group A", groupDTO.getName());
        assertNull(groupDTO.getMembers(), "Members deve ser nulo na conversão");
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
                .build();

        List<GroupDTO> groupDTOs = groupConverter.convertToDto(List.of(group));

        assertNotNull(groupDTOs);
        assertEquals(1, groupDTOs.size());
        assertEquals(1L, groupDTOs.get(0).getId());
        assertEquals("Group A", groupDTOs.get(0).getName());
        assertNull(groupDTOs.get(0).getMembers(), "Members deve ser nulo na conversão");
    }

    @Test
    @DisplayName("Should update existing GroupEntity from GroupDTO without changing ID")
    void testUpdateExistingEntity() {
        GroupEntity existingEntity = GroupEntity.builder()
                .id(10L)
                .name("Old Name")
                .build();

        GroupDTO updatedDTO = new GroupDTO(99L, "Updated Name", List.of());

        GroupEntity updatedEntity = groupConverter.convertToEntity(updatedDTO, existingEntity);

        assertNotNull(updatedEntity);
        assertEquals(10L, updatedEntity.getId(), "ID original deve ser preservado");
        assertEquals("Updated Name", updatedEntity.getName(), "Nome deve ser atualizado");
    }
}