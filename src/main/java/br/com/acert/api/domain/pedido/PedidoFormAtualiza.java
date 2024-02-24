package br.com.acert.api.domain.pedido;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record PedidoFormAtualiza(
        @NotNull
        Long pedidoId,
        @NotBlank
        String descricao
) {
}
