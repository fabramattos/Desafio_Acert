package br.com.acert.api.domain.pedido;

import br.com.acert.api.domain.entrega.EntregaViewSimples;

public record PedidoViewComEntrega(
        Long idPedido,
        String descricao,
        EntregaViewSimples entrega
) {
    public PedidoViewComEntrega(Pedido pedido) {
        this(pedido.getId(), pedido.getDescricao(),
                pedido.getEntrega() != null ? new EntregaViewSimples(pedido.getEntrega()) : null);
    }
}
