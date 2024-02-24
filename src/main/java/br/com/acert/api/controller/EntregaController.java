package br.com.acert.api.controller;

import br.com.acert.api.domain.entrega.*;
import br.com.acert.api.service.EntregaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/entrega")
@SecurityRequirement(name = "bearer-key")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntregaViewSimples> criaEntrega(@RequestBody @Valid EntregaFormNovo form){
        var entrega = service.criar(form);
        return ResponseEntity.ok(new EntregaViewSimples(entrega));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<EntregaViewSimples> alteraEntrega(@RequestBody @Valid EntregaFormAtualiza form){
        var entrega = service.atualizar(form);
        return ResponseEntity.ok(new EntregaViewSimples(entrega));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<EntregaView> detalhaEntrega(@PathVariable Long id){
        var entrega = service.buscar(id);
        return ResponseEntity.ok(new EntregaView(entrega));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<EntregaView>> listaEntregas(){
        var entregas = service
                .listar()
                .stream()
                .map(EntregaView::new)
                .toList();

        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<EntregaView>> listaEntregasTodosClientes(){
        var entregas = service
                .listarDeTodosClientes() //TODO aplicar paginação
                .stream()
                .map(EntregaView::new)
                .toList();

        return ResponseEntity.ok(entregas);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaEntrega(@PathVariable Long id){
        service.deletar(id);
    }
}
