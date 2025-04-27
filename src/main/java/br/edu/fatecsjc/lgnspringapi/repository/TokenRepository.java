package br.edu.fatecsjc.lgnspringapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.fatecsjc.lgnspringapi.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("select t from Token t where t.user.id = :id and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByTokenValue(String tokenValue);
}