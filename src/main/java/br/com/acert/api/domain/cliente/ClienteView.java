package br.com.acert.api.domain.cliente;

import br.com.acert.api.domain.pedido.PedidoView;

import java.util.List;

public record ClienteView(Long id, String nome, String login, List<PedidoView> pedidos) {

    public ClienteView(Cliente cliente) {
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getLogin(),
                cliente.getPedidos()
                        .stream()
                        .map(PedidoView::new)
                        .toList()
        );
    }
}
