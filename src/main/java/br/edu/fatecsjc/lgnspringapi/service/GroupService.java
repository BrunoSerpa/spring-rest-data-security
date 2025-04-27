package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupConverter groupConverter;
    private final MemberConverter memberConverter;

    public GroupService(GroupRepository groupRepository,
            MemberRepository memberRepository,
            GroupConverter groupConverter,
            MemberConverter memberConverter) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.groupConverter = groupConverter;
        this.memberConverter = memberConverter;
    }

    public List<GroupDTO> getAll() {
        return groupConverter.convertToDto(groupRepository.findAll());
    }

    public GroupDTO findById(Long id) {
        return groupRepository.findById(id)
                .map(groupConverter::convertToDto)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }

    @Transactional
    public GroupDTO save(Long id, GroupDTO dto) {
        GroupEntity entity = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        memberRepository.deleteByGroup(entity);

        List<Member> membersToSave = dto.getMembers()
                .stream()
                .map(memberDTO -> memberConverter.convertToEntity(memberDTO))
                .collect(Collectors.toList());

        memberRepository.saveAll(membersToSave);

        GroupEntity groupToSave = groupConverter.convertToEntity(dto, entity);

        GroupEntity savedGroup = groupRepository.save(groupToSave);
        return groupConverter.convertToDto(savedGroup);
    }

    public GroupDTO save(GroupDTO dto) {
        GroupEntity groupToSave = groupConverter.convertToEntity(dto);
        GroupEntity savedGroup = groupRepository.save(groupToSave);
        return groupConverter.convertToDto(savedGroup);
    }

    @Transactional
    public void delete(Long id) {
        GroupEntity group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        memberRepository.deleteByGroup(group);
        groupRepository.deleteById(id);
    }
}
