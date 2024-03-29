package br.com.acert.api.controller;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormLogin;
import br.com.acert.api.infra.security.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "Realiza processo de login.")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping
    @Operation(summary = "Realiza autenticação do usuario/senha e retorna um jwt válido para as requests")
    public ResponseEntity<String> login(@RequestBody @Valid ClienteFormLogin form){
        var login = new UsernamePasswordAuthenticationToken(form.login(), form.senha());
        var authentication = manager.authenticate(login);
        var jwt = tokenUtils.geraToken((Cliente) authentication.getPrincipal());

        return ResponseEntity.ok(jwt);
    }
}
