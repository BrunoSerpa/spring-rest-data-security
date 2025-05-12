package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupConverter implements Converter<GroupEntity, GroupDTO> {
    private final ModelMapper modelMapper;

    public GroupConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private TypeMap<GroupDTO, GroupEntity> propertyMapperDto;

    @Override
    public GroupEntity convertToEntity(GroupDTO dto) {
        if (propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(GroupDTO.class, GroupEntity.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(GroupEntity::setId));
        }
        return modelMapper.map(dto, GroupEntity.class);
    }

    @Override
    public GroupEntity convertToEntity(GroupDTO dto, GroupEntity entity) {
        if (propertyMapperDto == null) {
            propertyMapperDto = modelMapper.createTypeMap(GroupDTO.class, GroupEntity.class);
            propertyMapperDto.addMappings(mapper -> mapper.skip(GroupEntity::setId));
        }
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
        return modelMapper.map(dtos, new TypeToken<List<GroupEntity>>() {}.getType());
    }

    @Override
    public List<GroupDTO> convertToDto(List<GroupEntity> entities) {
        return modelMapper.map(entities, new TypeToken<List<GroupDTO>>() {
        }.getType());
    }
}