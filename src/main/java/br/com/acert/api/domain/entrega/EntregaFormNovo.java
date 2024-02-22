package br.com.acert.api.domain.entrega;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public record EntregaFormNovo(
        @NotBlank
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
        @NotBlank
        Integer numero,
        String complemento
) {
}
