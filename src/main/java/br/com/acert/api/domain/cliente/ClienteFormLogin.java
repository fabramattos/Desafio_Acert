package br.com.acert.api.domain.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record ClienteFormLogin(
        @NotBlank @Email
        String login,
        @NotBlank
        String senha) {
}
