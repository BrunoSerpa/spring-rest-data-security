package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.converter.MemberConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupConverter groupConverter;

    @Mock
    private MemberConverter memberConverter;

    @InjectMocks
    private GroupService groupService;

    private GroupEntity groupEntity;
    private GroupDTO groupDTO;
    private Member member;
    private MemberDTO memberDTO;
    private List<Member> members;
    private List<MemberDTO> memberDTOs;

    @BeforeEach
    void setUp() {
        groupEntity = GroupEntity.builder()
                .id(1L)
                .name("Test Group")
                .build();

        member = Member.builder()
                .id(1L)
                .name("John Doe")
                .age(30)
                .group(groupEntity)
                .build();

        memberDTO = MemberDTO.builder()
                .id(1L)
                .name("John Doe")
                .age(30)
                .build();

        groupDTO = GroupDTO.builder()
                .id(1L)
                .name("Test Group")
                .members(List.of(memberDTO))
                .build();

        members = List.of(member);
        memberDTOs = List.of(memberDTO);
    }

    @Test
    @DisplayName("Should get all groups with their members")
    void getAll_Success() {
        when(groupRepository.findAll()).thenReturn(Arrays.asList(groupEntity));
        when(groupConverter.convertToDto(Arrays.asList(groupEntity))).thenReturn(Arrays.asList(groupDTO));
        when(memberRepository.findByGroupId(groupDTO.getId())).thenReturn(members);
        when(memberConverter.convertToDto(members)).thenReturn(memberDTOs);

        List<GroupDTO> result = groupService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(groupDTO.getId(), result.get(0).getId());
        assertEquals(1, result.get(0).getMembers().size());
    }

    @Test
    @DisplayName("Should find group by id with members")
    void findById_Success() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(groupEntity));
        when(groupConverter.convertToDto(groupEntity)).thenReturn(groupDTO);
        when(memberRepository.findByGroupId(1L)).thenReturn(members);
        when(memberConverter.convertToDto(members)).thenReturn(memberDTOs);

        GroupDTO result = groupService.findById(1L);

        assertNotNull(result);
        assertEquals(groupDTO.getId(), result.getId());
        assertEquals(groupDTO.getName(), result.getName());
        assertEquals(1, result.getMembers().size());
    }

    @Test
    @DisplayName("Should throw exception when group not found")
    void findById_NotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> groupService.findById(1L));
    }

    @Test
    @DisplayName("Should create new group with members")
    void save_NewGroup_Success() {
        when(groupConverter.convertToEntity(groupDTO)).thenReturn(groupEntity);
        when(groupRepository.save(groupEntity)).thenReturn(groupEntity);
        when(memberConverter.convertToEntity(memberDTO)).thenReturn(member);
        when(memberRepository.saveAll(any())).thenReturn(members);
        when(groupConverter.convertToDto(groupEntity)).thenReturn(groupDTO);
        when(memberConverter.convertToDto(members)).thenReturn(memberDTOs);

        GroupDTO result = groupService.save(groupDTO);

        assertNotNull(result);
        assertEquals(groupDTO.getId(), result.getId());
        assertEquals(groupDTO.getName(), result.getName());
        assertEquals(1, result.getMembers().size());
    }

    @Test
    @DisplayName("Should update existing group and its members")
    void save_ExistingGroup_Success() {
        Long groupId = 1L;
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groupEntity));
        when(groupConverter.convertToEntity(eq(groupDTO), any())).thenReturn(groupEntity);
        when(groupRepository.save(groupEntity)).thenReturn(groupEntity);
        when(memberConverter.convertToEntity(memberDTO)).thenReturn(member);
        when(memberRepository.saveAll(any())).thenReturn(members);
        when(groupConverter.convertToDto(groupEntity)).thenReturn(groupDTO);
        when(memberConverter.convertToDto(members)).thenReturn(memberDTOs);

        GroupDTO result = groupService.save(groupId, groupDTO);

        assertNotNull(result);
        verify(memberRepository).deleteByGroup(groupEntity);
        assertEquals(groupDTO.getId(), result.getId());
        assertEquals(1, result.getMembers().size());
    }

    @Test
    @DisplayName("Should delete group and associated members")
    void delete_Success() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(groupEntity));

        groupService.delete(1L);

        verify(memberRepository).deleteByGroup(groupEntity);
        verify(groupRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when trying to delete non-existent group")
    void delete_NonExistentGroup() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> groupService.delete(1L));

        verify(memberRepository, never()).deleteByGroup(any());
        verify(groupRepository, never()).deleteById(any());
    }
}
