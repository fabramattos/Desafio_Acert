package br.com.acert.api.domain.entrega;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EntregaFormNovo(
        @NotNull
        Long pedidoId,
        @NotBlank
        String UF,
        @NotBlank
        String cidade,
        @NotBlank @Digits(message = "apenas dígitos aceitos", integer = 8, fraction = 0)
        String cep,
        @NotBlank
        String bairro,
        @NotBlank
        String logradouro,
        @NotNull
        Integer numero,
        String complemento
) {
}
