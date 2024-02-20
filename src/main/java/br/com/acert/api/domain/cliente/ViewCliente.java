package br.com.acert.api.domain.cliente;

public record ViewCliente(Long id, String nome, String login) {

    public ViewCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getLogin());
    }
}
