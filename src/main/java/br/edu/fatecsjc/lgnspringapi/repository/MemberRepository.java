package br.edu.fatecsjc.lgnspringapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatecsjc.lgnspringapi.entity.GroupEntity;
import br.edu.fatecsjc.lgnspringapi.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    void deleteByGroup(GroupEntity group);

}
