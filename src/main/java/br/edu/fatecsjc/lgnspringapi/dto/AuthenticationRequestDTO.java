package br.edu.fatecsjc.lgnspringapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {
    @NotNull
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull
    private String password;
}