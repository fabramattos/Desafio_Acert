package br.com.acert.api.infra.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado");
    }
}
