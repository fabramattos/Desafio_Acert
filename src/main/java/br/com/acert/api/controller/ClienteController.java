package br.com.acert.api.controller;

import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteView;
import br.com.acert.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteView> criaCliente(@RequestBody @Valid ClienteFormNovo form){
        //TODO usar encoder de senha
        var cliente = service.criar(form);
        return ResponseEntity.ok(new ClienteView(cliente));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ClienteView> alteraCliente(@RequestBody @Valid ClienteFormAtualiza form){
        var cliente = service.alterar(form);
        return ResponseEntity.ok(new ClienteView(cliente));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ClienteView> detalhaCliente(@PathVariable Long id){
        var cliente = service.consultar(id);
        return ResponseEntity.ok(new ClienteView(cliente));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<ClienteView>> listaClientes(){
        //TODO criar acesso admin
        var clientes = service
                .listar()
                .stream()
                .map(ClienteView::new)
                .toList();

        return ResponseEntity.ok(clientes);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaCliente(@PathVariable Long id){
        service.deletar(id);
    }



}
