package br.com.acert.api.controller;

import br.com.acert.api.domain.pedido.PedidoFormAtualiza;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoViewSimples;
import br.com.acert.api.domain.pedido.PedidoViewComEntrega;
import br.com.acert.api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedido")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pedido", description = "Operações relacionadas com Pedido.")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo pedido")
    public ResponseEntity<PedidoViewSimples> criaPedido(@RequestBody @Valid PedidoFormNovo form){
        var pedido = service.criar(form);
        return ResponseEntity.ok(new PedidoViewSimples(pedido));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Altera um pedido SE entrega não estiver em andamento")
    public ResponseEntity<PedidoViewSimples> alteraPedido(@RequestBody @Valid PedidoFormAtualiza form){
        var pedido = service.alterar(form);
        return ResponseEntity.ok(new PedidoViewSimples(pedido));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Detalha o pedido do ID informado")
    public ResponseEntity<PedidoViewComEntrega> detalhaPedido(@PathVariable Long id){
        var pedido = service.consultar(id);
        return ResponseEntity.ok(new PedidoViewComEntrega(pedido));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Retorna todos pedidos do cliente logado")
    public ResponseEntity<List<PedidoViewComEntrega>> listaPedidos(){
        var pedidos = service
                .listar()
                .stream()
                .map(PedidoViewComEntrega::new)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta pedido SE entrega não estiver em andamento e deleta entrega caso gerada.")
    public void deletaPedido(@PathVariable Long id){
        service.deletar(id);
    }

}
