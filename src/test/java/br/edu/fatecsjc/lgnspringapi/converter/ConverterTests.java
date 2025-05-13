package br.edu.fatecsjc.lgnspringapi.converter;

import java.util.List;
import java.util.stream.Collectors;

public class ConverterTests implements Converter<EntityTest, DTOTest> {
    @Override
    public EntityTest convertToEntity(DTOTest dto) {
        if (dto == null)
            return null;
        return new EntityTest(dto.getValue());
    }

    @Override
    public EntityTest convertToEntity(DTOTest dto, EntityTest entity) {
        if (dto == null || entity == null)
            return null;
        entity.setValue(dto.getValue());
        return entity;
    }

    @Override
    public DTOTest convertToDto(EntityTest entity) {
        if (entity == null)
            return null;
        return new DTOTest(entity.getValue());
    }

    @Override
    public List<EntityTest> convertToEntity(List<DTOTest> dtos) {
        if (dtos == null)
            return null;
        return dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DTOTest> convertToDto(List<EntityTest> entities) {
        if (entities == null)
            return null;
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}