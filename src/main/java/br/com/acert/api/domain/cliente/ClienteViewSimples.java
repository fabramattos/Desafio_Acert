package br.com.acert.api.domain.cliente;

public record ClienteViewSimples(
        long clienteId,
        String nome,
        String login
) {

    public ClienteViewSimples(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getLogin());
    }
}
