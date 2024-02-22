package br.com.acert.api.controller;

import br.com.acert.api.domain.entrega.EntregaFormAtualiza;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaView;
import br.com.acert.api.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/entrega")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntregaView> criaEntrega(@RequestBody @Valid EntregaFormNovo form){
        var entrega = service.criar(form);
        return ResponseEntity.ok(new EntregaView(entrega));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<EntregaView> alteraEntrega(@RequestBody @Valid EntregaFormAtualiza form){
        var entrega = service.atualizar(form);
        return ResponseEntity.ok(new EntregaView(entrega));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<EntregaView> detalhaEntrega(@PathVariable Long id){
        var entrega = service.detalhar(id);
        return ResponseEntity.ok(new EntregaView(entrega));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<EntregaView>> listaEntregas(){
        //TODO criar acesso admin
        var entregas = service
                .listar()
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
