package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationConverter implements Converter<Organization, OrganizationDTO> {
    private final ModelMapper modelMapper;
    private final TypeMap<OrganizationDTO, Organization> propertyMapperDto;

    public OrganizationConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.propertyMapperDto = this.modelMapper.createTypeMap(OrganizationDTO.class, Organization.class);
        this.propertyMapperDto.addMappings(mapper -> mapper.skip(Organization::setId));
    }

    @Override
    public Organization convertToEntity(OrganizationDTO dto) {
        return modelMapper.map(dto, Organization.class);
    }

    @Override
    public Organization convertToEntity(OrganizationDTO dto, Organization entity) {
        Provider<Organization> groupProvider = p -> entity;
        propertyMapperDto.setProvider(groupProvider);
        return modelMapper.map(dto, Organization.class);
    }

    @Override
    public OrganizationDTO convertToDto(Organization entity) {
        return modelMapper.map(entity, OrganizationDTO.class);
    }

    @Override
    public List<Organization> convertToEntity(List<OrganizationDTO> dtos) {
        if (dtos == null)
            return null;
        return dtos.stream().map(this::convertToEntity).toList();
    }

    @Override
    public List<OrganizationDTO> convertToDto(List<Organization> entities) {
        if (entities == null)
            return null;
        return entities.stream().map(this::convertToDto).toList();
    }
}