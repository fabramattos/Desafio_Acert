package br.com.acert.api.controller;

import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteViewCompleto;
import br.com.acert.api.domain.cliente.ClienteViewSimples;
import br.com.acert.api.service.ClienteService;
import br.com.acert.api.infra.security.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/cliente")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Cliente", description = "Operações relacionadas com Cliente.")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    TokenUtils tokenUtils;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo cliente",
            security = @SecurityRequirement(name = "", scopes = {}))
    public ResponseEntity<ClienteViewSimples> criaCliente(@RequestBody @Valid ClienteFormNovo form,
                                                          UriComponentsBuilder uriBuilder) {
        var cliente = service.criar(form);
        var uri = uriBuilder
                .path("/cliente/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(new ClienteViewSimples(cliente));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "altera cliente")
    public ResponseEntity<ClienteViewSimples> alteraCliente(@RequestBody @Valid ClienteFormAtualiza form,
                                                            HttpServletRequest request) {
        var idUser = tokenUtils.getUserId(request);
        var cliente = service.alterar(idUser, form);
        return ResponseEntity.ok(new ClienteViewSimples(cliente));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "consulta dados do cliente, seus pedidos e entregas")
    public ResponseEntity<ClienteViewCompleto> detalhaClienteLogado(HttpServletRequest request) {
        var idUser = tokenUtils.getUserId(request);
        var cliente = service.buscar(idUser);
        return ResponseEntity.ok(new ClienteViewCompleto(cliente));
    }

    @Transactional
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta cliente")
    public void deletaCliente(HttpServletRequest request) {
        var idUser = tokenUtils.getUserId(request);
        service.deletar(idUser);
    }


}
