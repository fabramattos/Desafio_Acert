package br.com.acert.api.controller;

import br.com.acert.api.domain.pedido.PedidoFormAtualiza;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoViewComEntrega;
import br.com.acert.api.domain.pedido.PedidoViewSimples;
import br.com.acert.api.infra.security.TokenUtils;
import br.com.acert.api.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pedido", description = "Operações relacionadas com Pedido.")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private TokenUtils tokenUtils;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo pedido")
    public ResponseEntity<PedidoViewSimples> criaPedido(@RequestBody @Valid PedidoFormNovo form,
                                                        HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var pedido = service.criar(userId, form);
        return ResponseEntity.ok(new PedidoViewSimples(pedido));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Altera um pedido SE entrega não estiver em andamento")
    public ResponseEntity<PedidoViewSimples> alteraPedido(@RequestBody @Valid PedidoFormAtualiza form,
                                                          HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var pedido = service.alterar(userId, form);
        return ResponseEntity.ok(new PedidoViewSimples(pedido));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Detalha o pedido do ID informado")
    public ResponseEntity<PedidoViewComEntrega> detalhaPedido(@PathVariable Long id,
                                                              HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var pedido = service.buscar(userId, id);
        return ResponseEntity.ok(new PedidoViewComEntrega(pedido));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Retorna todos pedidos do cliente logado")
    public ResponseEntity<List<PedidoViewComEntrega>> listaPedidos( HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var pedidos = service
                .listar(userId)
                .stream()
                .map(PedidoViewComEntrega::new)
                .toList();

        return ResponseEntity.ok(pedidos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta pedido SE entrega não estiver em andamento e deleta entrega caso gerada.")
    public void deletaPedido(@PathVariable Long id,
                             HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        service.deletar(userId, id);
    }

}
