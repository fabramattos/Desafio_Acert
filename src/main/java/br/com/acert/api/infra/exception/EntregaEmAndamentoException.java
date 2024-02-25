package br.com.acert.api.infra.exception;

public class EntregaEmAndamentoException extends RuntimeException{
    public EntregaEmAndamentoException() {
        super("Não é possível alterar. Existe uma entrega em andamento!");
    }
}
