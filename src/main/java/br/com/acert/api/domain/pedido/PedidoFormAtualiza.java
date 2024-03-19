package br.com.acert.api.domain.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoFormAtualiza(
        @NotNull @Schema(required = true)
        Long pedidoId,
        @NotBlank @Schema(required = true)
        String descricao
) {
}
