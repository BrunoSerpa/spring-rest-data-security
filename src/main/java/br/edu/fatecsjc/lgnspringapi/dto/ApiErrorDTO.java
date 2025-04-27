package br.edu.fatecsjc.lgnspringapi.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDTO {
    private String message;

    @Builder.Default
    private Instant timestamp = Instant.now();
}