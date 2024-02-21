package br.com.acert.api.controller;

import br.com.acert.api.domain.pedido.PedidoFormAtualiza;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoView;
import br.com.acert.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PedidoView> criaPedido(@RequestBody @Valid PedidoFormNovo form){
        var pedido = service.criar(form);
        return ResponseEntity.ok(new PedidoView(pedido));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<PedidoView> alteraPedido(@RequestBody @Valid PedidoFormAtualiza form){
        var pedido = service.alterar(form);
        return ResponseEntity.ok(new PedidoView(pedido));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<PedidoView> detalhaPedido(@PathVariable Long id){
        var pedido = service.consultar(id);
        return ResponseEntity.ok(new PedidoView(pedido));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<PedidoView>> listaPedidos(){
        //TODO criar acesso admin
        var clientes = service
                .listar()
                .stream()
                .map(PedidoView::new)
                .toList();

        return ResponseEntity.ok(clientes);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletaPedido(@PathVariable Long id){
        service.deletar(id);
    }

}
