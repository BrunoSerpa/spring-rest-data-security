package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @DisplayName("Should save and retrieve groups")
    void testSaveAndRetrieveGroups() {
        GroupEntity group = GroupEntity.builder()
            .name("Group A")
            .build();
        groupRepository.save(group);

        List<GroupEntity> groups = groupRepository.findAll();

        assertEquals(1, groups.size());
        assertEquals("Group A", groups.get(0).getName());
    }
}