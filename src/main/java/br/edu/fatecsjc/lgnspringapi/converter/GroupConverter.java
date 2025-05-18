package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupConverter implements Converter<GroupEntity, GroupDTO> {
    private final ModelMapper modelMapper;
    private final TypeMap<GroupDTO, GroupEntity> propertyMapperDto;

    public GroupConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.propertyMapperDto = this.modelMapper.createTypeMap(GroupDTO.class, GroupEntity.class);
        this.propertyMapperDto.addMappings(mapper -> mapper.skip(GroupEntity::setId));
    }

    @Override
    public GroupEntity convertToEntity(GroupDTO dto) {
        return modelMapper.map(dto, GroupEntity.class);
    }

    @Override
    public GroupEntity convertToEntity(GroupDTO dto, GroupEntity entity) {
        Provider<GroupEntity> groupProvider = p -> entity;
        propertyMapperDto.setProvider(groupProvider);
        return modelMapper.map(dto, GroupEntity.class);
    }

    @Override
    public GroupDTO convertToDto(GroupEntity entity) {
        return modelMapper.map(entity, GroupDTO.class);
    }

    @Override
    public List<GroupEntity> convertToEntity(List<GroupDTO> dtos) {
        if (dtos == null)
            return null;
        return dtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public List<GroupDTO> convertToDto(List<GroupEntity> entities) {
        if (entities == null)
            return null;
        return entities.stream().map(this::convertToDto).toList();
    }
}