package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.AddressConverter;
import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.converter.OrganizationConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Address;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.AddressRepository;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final GroupRepository groupRepository;
    private final AddressRepository addressRepository;

    private final OrganizationConverter organizationConverter;
    private final GroupConverter groupConverter;
    private final AddressConverter addressConverter;

    private final GroupService groupService;

    public List<OrganizationDTO> findAll() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream()
                .map(org -> {
                    OrganizationDTO dto = organizationConverter.convertToDto(org);
                    dto.setEndereco(addressConverter.convertToDto(addressRepository.findByOrganizationId(org.getId())));
                    dto.setGrupos(groupConverter.convertToDto(groupRepository.findByOrganizationId(org.getId())));
                    return dto;
                })
                .toList();
    }

    public OrganizationDTO findById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Organization not found"));

        OrganizationDTO organizationDTO = organizationConverter.convertToDto(organization);
        organizationDTO.setEndereco(addressConverter.convertToDto(addressRepository.findByOrganizationId(id)));
        organizationDTO.setGrupos(groupConverter.convertToDto(groupRepository.findByOrganizationId(id)));

        return organizationDTO;
    }

    @Transactional
    public OrganizationDTO save(Long id, OrganizationDTO dto) {
        Organization entity = organizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Organization not found"));

        groupRepository.nullifyOrganizationId(id);

        Organization updatedOrganization = organizationConverter.convertToEntity(dto, entity);
        Organization savedOrganization = organizationRepository.save(updatedOrganization);
        List<GroupEntity> newGroups = dto.getGrupos().stream()
                .map(groupDTO -> {
                    // Salvar grupo usando GroupService que já lida com membros
                    GroupDTO savedGroup = groupService.save(groupDTO);
                    // Buscar entidade salva e atualizar organização
                    GroupEntity groupEntity = groupRepository.findById(savedGroup.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Group not found after save"));
                    groupEntity.setOrganization(savedOrganization);
                    return groupEntity;
                })
                .toList();

        List<GroupEntity> existingGroups = dto.getGruposId().stream()
                .map(groupId -> {
                    GroupEntity group = groupRepository.findById(groupId)
                            .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
                    group.setOrganization(savedOrganization);
                    return group;
                })
                .toList();

        List<GroupEntity> allGroups = new java.util.ArrayList<>();
        allGroups.addAll(newGroups);
        allGroups.addAll(existingGroups);
        groupRepository.saveAll(allGroups);

        Address address = addressConverter.convertToEntity(dto.getEndereco());
        address.setOrganization(savedOrganization);

        addressRepository.save(address);

        OrganizationDTO returnedDTO = organizationConverter.convertToDto(savedOrganization);
        returnedDTO.setEndereco(dto.getEndereco());
        returnedDTO.setGrupos(groupConverter.convertToDto(allGroups));
        return returnedDTO;
    }

    public OrganizationDTO save(OrganizationDTO dto) {
        Organization organizationToSave = organizationConverter.convertToEntity(dto);
        Organization savedOrganization = organizationRepository.save(organizationToSave);

        List<GroupEntity> newGroups = dto.getGrupos().stream()
                .map(groupDTO -> {
                    // Salvar grupo usando GroupService que já lida com membros
                    GroupDTO savedGroup = groupService.save(groupDTO);
                    // Buscar entidade salva e atualizar organização
                    GroupEntity groupEntity = groupRepository.findById(savedGroup.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Group not found after save"));
                    groupEntity.setOrganization(savedOrganization);
                    return groupEntity;
                })
                .toList();

        List<GroupEntity> existingGroups = dto.getGruposId().stream()
                .map(groupId -> {
                    GroupEntity group = groupRepository.findById(groupId)
                            .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
                    group.setOrganization(savedOrganization);
                    return group;
                })
                .toList();

        List<GroupEntity> allGroups = new java.util.ArrayList<>();
        allGroups.addAll(newGroups);
        allGroups.addAll(existingGroups);
        groupRepository.saveAll(allGroups);

        Address address = addressConverter.convertToEntity(dto.getEndereco());
        address.setOrganization(savedOrganization);

        addressRepository.save(address);

        OrganizationDTO returnedDTO = organizationConverter.convertToDto(savedOrganization);
        returnedDTO.setEndereco(dto.getEndereco());
        returnedDTO.setGrupos(groupConverter.convertToDto(allGroups));
        return returnedDTO;
    }

    @Transactional
    public void deleteById(Long id) {
        organizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Organização com id " + id + " não encontrada"));

        groupRepository.nullifyOrganizationId(id);

        Address address = addressRepository.findByOrganizationId(id);
        if (address != null) {
            addressRepository.delete(address);
        }

        organizationRepository.deleteById(id);
    }
}