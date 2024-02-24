package br.com.acert.api.domain.cliente;

import br.com.acert.api.domain.pedido.PedidoViewComEntrega;

import java.util.List;

public record ClienteViewCompleto(
        long clienteId,
        String nome,
        String login,
        List<PedidoViewComEntrega> pedidos
) {

    public ClienteViewCompleto(Cliente cliente) {
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getLogin(),
                cliente.getPedidos()
                        .stream()
                        .map(PedidoViewComEntrega::new)
                        .toList()
        );
    }
}
