package br.com.acert.api.infra.exception;

public class LoginException extends RuntimeException{
    public LoginException() {
        super("Usuário inexistente ou senha inválida");
    }
}
