package br.com.acert.api.domain.pedido;

import br.com.acert.api.domain.cliente.ClienteView;

public record PedidoView(
        Long id,
        ClienteView cliente,
        String descricao
) {
    public PedidoView(Pedido pedido) {
        this(pedido.getId(), new ClienteView(pedido.getCliente()), pedido.getDescricao());
    }
}
