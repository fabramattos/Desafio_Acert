package br.com.acert.api.infra.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<ExceptionView> tratarSenhaInvalida(BadCredentialsException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    ResponseEntity<ExceptionView> tratarUsuarioInvalido(){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView("Usuário inexistente ou senha inválida"));
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

    @ExceptionHandler(EntregaEmAndamentoException.class)
    ResponseEntity<ExceptionView> tratarEntregaEmAndamento(EntregaEmAndamentoException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }

    @ExceptionHandler(EntregaExistenteException.class)
    ResponseEntity<ExceptionView> tratarEntregaExistente(EntregaExistenteException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionView> tratarValidacaoDeArgumentos(MethodArgumentNotValidException e){
        return ResponseEntity
                .badRequest()
                .body(new ExceptionView(e.getLocalizedMessage()));
    }

}
