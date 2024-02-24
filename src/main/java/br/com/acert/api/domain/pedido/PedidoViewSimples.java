package br.com.acert.api.domain.pedido;

public record PedidoViewSimples(
        long idCliente,
        long idPedido,
        String descricao
) {
    public PedidoViewSimples(Pedido pedido) {
        this(pedido.getCliente().getId(), pedido.getId(), pedido.getDescricao());
    }
}
