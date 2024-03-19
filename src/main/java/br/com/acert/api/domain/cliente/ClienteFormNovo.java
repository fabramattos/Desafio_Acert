package br.com.acert.api.domain.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteFormNovo(
        @NotBlank @Schema(required = true)
        String nome,
        @Email @Schema(required = true)
        String login,
        @NotBlank @Schema(required = true)
        String senha) {
}