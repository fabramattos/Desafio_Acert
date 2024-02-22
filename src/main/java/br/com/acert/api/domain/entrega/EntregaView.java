package br.com.acert.api.domain.entrega;

import br.com.acert.api.domain.pedido.PedidoView;

public record EntregaView(
        Long id,
        String UF,
        String cidade,
        String cep,
        String bairro,
        String logradouro,
        Integer numero,
        String complemento,
        EntregaStatus status,
        PedidoView pedido
) {

    public EntregaView(Entrega entrega){
        this(entrega.getId(),
                entrega.getUF(),
                entrega.getCidade(),
                entrega.getCep(),
                entrega.getBairro(),
                entrega.getLogradouro(),
                entrega.getNumero(),
                entrega.getComplemento(),
                entrega.getStatus(),
                new PedidoView(entrega.getPedido())
        );
    }
}
