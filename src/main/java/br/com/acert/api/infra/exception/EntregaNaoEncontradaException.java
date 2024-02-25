package br.com.acert.api.infra.exception;

public class EntregaNaoEncontradaException extends RuntimeException{
    public EntregaNaoEncontradaException() {
        super("Pedido não encontrado");
    }
}
