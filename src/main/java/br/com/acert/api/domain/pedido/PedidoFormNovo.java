package br.com.acert.api.domain.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PedidoFormNovo(
        @NotBlank @Schema(required = true)
        String descricao
) {
}
