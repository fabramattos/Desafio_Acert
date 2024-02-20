package br.com.acert.api.domain.cliente;

public record FormAtualizaCliente(
        Long id,
        String nome,
        String login,
        String senha) {
}
