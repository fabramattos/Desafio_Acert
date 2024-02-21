package br.com.acert.api.domain.pedido;

import javax.validation.constraints.NotBlank;

public record PedidoFormAtualiza(
        @NotBlank
        Long pedidoId,
        @NotBlank
        String descricao
) {
}
