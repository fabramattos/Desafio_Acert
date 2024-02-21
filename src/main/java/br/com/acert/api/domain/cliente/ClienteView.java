package br.com.acert.api.domain.cliente;

public record ClienteView(Long id, String nome, String login) {

    public ClienteView(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getLogin());
    }
}
