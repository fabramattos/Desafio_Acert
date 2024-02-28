package br.com.acert.api.infra.exception;

public class EntregaExistenteException extends RuntimeException{
    public EntregaExistenteException() {
        super("Ja existe entrega para ID informado!");
    }
}
