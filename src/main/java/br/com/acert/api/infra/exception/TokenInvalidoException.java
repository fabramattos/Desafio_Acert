package br.com.acert.api.infra.exception;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException() {
        super("JWT inválido ou expirado");
    }
}
