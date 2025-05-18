package br.edu.fatecsjc.lgnspringapi.converter;

import br.edu.fatecsjc.lgnspringapi.dto.AddressDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Address;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressConverter {
    private final ModelMapper modelMapper;

    public AddressConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Address convertToEntity(AddressDTO dto) {
        return modelMapper.map(dto, Address.class);
    }

    public AddressDTO convertToDto(Address entity) {
        return modelMapper.map(entity, AddressDTO.class);
    }

    public List<Address> convertToEntity(List<AddressDTO> dtos) {
        return modelMapper.map(dtos, new TypeToken<List<Address>>() {}.getType());
    }

    public List<AddressDTO> convertToDto(List<Address> entities) {
        return modelMapper.map(entities, new TypeToken<List<AddressDTO>>() {}.getType());
    }
}
