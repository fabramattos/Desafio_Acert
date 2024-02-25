package br.com.acert.api.infra.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(TokenExpiredException.class)
    ResponseEntity<ExceptionView> tratarTokenExpirado(){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView("Token expirado!"));
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    ResponseEntity<ExceptionView> tratarClienteNaoEncontrado(ClienteNaoEncontradoException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }

    @ExceptionHandler(EntregaNaoEncontradaException.class)
    ResponseEntity<ExceptionView> tratarEntregaNaoEncontrada(EntregaNaoEncontradaException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    ResponseEntity<ExceptionView> tratarPedidoNaoEncontradoa(PedidoNaoEncontradoException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }
}
