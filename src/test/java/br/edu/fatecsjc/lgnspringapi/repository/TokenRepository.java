package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Token;
import br.edu.fatecsjc.lgnspringapi.entity.User;
import br.edu.fatecsjc.lgnspringapi.enums.Role;
import br.edu.fatecsjc.lgnspringapi.enums.TokenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TokenRepositoryTest {

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("Should find all valid tokens by user ID")
  void testFindAllValidTokenByUser() {
    User user = User.builder()
      .firstName("John")
      .lastName("Doe")
      .email("john.doe@example.com")
      .password("password")
      .role(Role.USER)
      .build();

    userRepository.save(user);

    Token validToken = Token.builder()
      .tokenValue("validToken")
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .user(user)
      .build();

    Token expiredToken = Token.builder()
      .tokenValue("expiredToken")
      .tokenType(TokenType.BEARER)
      .expired(true)
      .revoked(true)
      .user(user)
      .build();

    tokenRepository.saveAll(List.of(validToken, expiredToken));

    List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getId());

    assertEquals(1, validTokens.size());
    assertEquals("validToken", validTokens.get(0).getTokenValue());
  }

  @Test
  @DisplayName("Should find token by token value")
  void testFindByTokenValue() {
    Token token = Token.builder()
      .tokenValue("sampleToken")
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .build();

    tokenRepository.save(token);

    Optional<Token> foundToken = tokenRepository.findByTokenValue("sampleToken");

    assertTrue(foundToken.isPresent());
    assertEquals("sampleToken", foundToken.get().getTokenValue());
  }
}