package br.com.acert.api.controller;

import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteViewCompleto;
import br.com.acert.api.domain.cliente.ClienteViewSimples;
import br.com.acert.api.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
@SecurityRequirement(name = "bearer-key")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
    public ResponseEntity<ClienteViewSimples> alteraCliente(@RequestBody @Valid ClienteFormAtualiza form) {
        var cliente = service.alterarIdAutenticado(form);
        return ResponseEntity.ok(new ClienteViewSimples(cliente));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ClienteViewCompleto> detalhaClienteLogado() {
        var cliente = service.consultarIdAutenticado();
        return ResponseEntity.ok(new ClienteViewCompleto(cliente));
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<ClienteViewSimples>> listaClientes() {
        var clientes = service
                .listar()
                .stream()
                .map(ClienteViewSimples::new)
                .toList();

        return ResponseEntity.ok(clientes);
    }

    @Transactional
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaCliente() {
        service.deletarIdAutenticado();
    }


}
