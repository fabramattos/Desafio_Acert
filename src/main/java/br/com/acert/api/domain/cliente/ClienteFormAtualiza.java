package br.com.acert.api.domain.cliente;

public record ClienteFormAtualiza(
        Long id,
        String nome,
        String login,
        String senha) {
}
