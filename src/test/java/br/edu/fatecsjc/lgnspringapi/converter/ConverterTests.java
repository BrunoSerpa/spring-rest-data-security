package br.edu.fatecsjc.lgnspringapi.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTests {
    private ConverterTest converter;

    @BeforeEach
    public void setup() {
        converter = new ConverterTest();
    }

    @Test
    public void testConvertToEntity() {
        DTOTest dto = new DTOTest("test");
        EntityTest entity = converter.convertToEntity(dto);
        assertNotNull(entity, "A entidade não deve ser nula");
        assertEquals("test", entity.getValue(), "O valor da entidade deve vir do DTO");
    }

    @Test
    public void testConvertToEntityWithExistingEntity() {
        DTOTest dto = new DTOTest("updated");
        EntityTest entity = new EntityTest("original");
        EntityTest updatedEntity = converter.convertToEntity(dto, entity);
        assertNotNull(updatedEntity, "A entidade atualizada não deve ser nula");
        assertEquals("updated", updatedEntity.getValue(), "O valor deve ser atualizado a partir do DTO");
    }

    @Test
    public void testConvertToDto() {
        EntityTest entity = new EntityTest("entityValue");
        DTOTest dto = converter.convertToDto(entity);
        assertNotNull(dto, "O DTO não deve ser nulo");
        assertEquals("entityValue", dto.getValue(), "O valor do DTO deve vir da entidade");
    }

    @Test
    public void testConvertToEntityList() {
        List<DTOTest> dtos = Arrays.asList(new DTOTest("1"), new DTOTest("2"), new DTOTest("3"));
        List<EntityTest> entities = converter.convertToEntity(dtos);
        assertNotNull(entities, "A lista de entidades não deve ser nula");
        assertEquals(3, entities.size(), "A lista de entidades deve conter 3 elementos");
        assertEquals("1", entities.get(0).getValue());
        assertEquals("2", entities.get(1).getValue());
        assertEquals("3", entities.get(2).getValue());
    }

    @Test
    public void testConvertToDtoList() {
        List<EntityTest> entities = Arrays.asList(new EntityTest("A"), new EntityTest("B"), new EntityTest("C"));
        List<DTOTest> dtos = converter.convertToDto(entities);
        assertNotNull(dtos, "A lista de DTOs não deve ser nula");
        assertEquals(3, dtos.size(), "A lista de DTOs deve conter 3 elementos");
        assertEquals("A", dtos.get(0).getValue());
        assertEquals("B", dtos.get(1).getValue());
        assertEquals("C", dtos.get(2).getValue());
    }    
}
