package br.com.acert.api.domain.entrega;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EntregaFormNovo(
        @NotNull @Schema(required = true)
        Long pedidoId,
        @NotBlank @Schema(required = true)
        String UF,
        @NotBlank @Schema(required = true)
        String cidade,
        @NotBlank @Digits(message = "apenas dígitos aceitos", integer = 8, fraction = 0) @Schema(required = true)
        String cep,
        @NotBlank @Schema(required = true)
        String bairro,
        @NotBlank @Schema(required = true)
        String logradouro,
        @NotNull @Schema(required = true)
        Integer numero,
        String complemento
) {
}
