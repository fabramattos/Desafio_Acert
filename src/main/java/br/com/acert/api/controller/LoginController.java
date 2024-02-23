package br.com.acert.api.controller;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormLogin;
import br.com.acert.api.service.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@SecurityRequirement(name = "bearer-key")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid ClienteFormLogin form){
        var login = new UsernamePasswordAuthenticationToken(form.login(), form.senha());
        var authentication = manager.authenticate(login);
        var jwt = tokenService.geraToken((Cliente) authentication.getPrincipal());

        return ResponseEntity.ok(jwt);
    }
}
