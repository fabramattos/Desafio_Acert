package br.com.acert.api.domain.pedido;

import javax.validation.constraints.NotBlank;

public record PedidoFormNovo(
        @NotBlank
        Long clientId,
        @NotBlank
        String descricao
) {
}
