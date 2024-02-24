package br.com.acert.api.domain.entrega;

import br.com.acert.api.domain.pedido.PedidoViewSimples;

public record EntregaView(
        EntregaViewSimples entrega,
        PedidoViewSimples pedido

) {

    public EntregaView(Entrega entrega) {
        this(new EntregaViewSimples(entrega), new PedidoViewSimples(entrega.getPedido()));
    }
}
