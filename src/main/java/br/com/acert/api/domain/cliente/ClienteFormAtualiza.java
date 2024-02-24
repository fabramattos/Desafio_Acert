package br.com.acert.api.domain.cliente;

public record ClienteFormAtualiza(
        String nome,
        String login,
        String senha) {
}
