package br.com.acert.api.domain.cliente;

import javax.validation.constraints.NotBlank;

public record ClienteFormNovo(
        @NotBlank
        String nome,
        @NotBlank
        String login,
        @NotBlank
        String senha) {
}
