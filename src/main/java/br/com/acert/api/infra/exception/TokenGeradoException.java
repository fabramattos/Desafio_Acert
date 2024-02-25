package br.com.acert.api.infra.exception;

public class TokenGeradoException extends RuntimeException{
    public TokenGeradoException() {
        super("Erro ao gerar JWT");
    }
}
