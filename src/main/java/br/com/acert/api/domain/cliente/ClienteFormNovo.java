package br.com.acert.api.domain.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record ClienteFormNovo(
        @NotBlank
        String nome,
        @Email
        String login,
        @NotBlank
        String senha) {
}