package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @DisplayName("Should delete members by group")
    void testDeleteMembersByGroup() {
        GroupEntity group = GroupEntity.builder()
            .name("Group A")
            .build();
        groupRepository.save(group);

        Member member = Member.builder()
            .name("Alice")
            .age(25)
            .group(group)
            .build();
        memberRepository.save(member);

        memberRepository.deleteByGroup(group);
        List<Member> members = memberRepository.findAll();

        assertTrue(members.isEmpty());
    }
}