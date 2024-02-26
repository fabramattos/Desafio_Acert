package br.com.acert.api.controller;

import br.com.acert.api.domain.entrega.EntregaFormAtualiza;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaView;
import br.com.acert.api.domain.entrega.EntregaViewSimples;
import br.com.acert.api.infra.security.TokenUtils;
import br.com.acert.api.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/entrega")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Entrega", description = "Operações relacionadas com Entregas.")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @Autowired
    private TokenUtils tokenUtils;

    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova entrega")
    public ResponseEntity<EntregaViewSimples> criaEntrega(@RequestBody @Valid EntregaFormNovo form,
                                                          HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var entrega = service.criar(userId, form);
        return ResponseEntity.ok(new EntregaViewSimples(entrega));
    }

    @Transactional
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Altera uma entrega SE não estiver em andamento. Valores válidos para statusEntrega: " +
            "PREPARANDO, A_CAMINHO, ENTREGUE, PROBLEMA_NA_ENTREGA")
    public ResponseEntity<EntregaViewSimples> alteraEntrega(@RequestBody @Valid EntregaFormAtualiza form,
                                                            HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var entrega = service.atualizar(userId, form);
        return ResponseEntity.ok(new EntregaViewSimples(entrega));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Detalha intrega do ID informado")
    public ResponseEntity<EntregaView> detalhaEntrega(@PathVariable Long id,
                                                      HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var entrega = service.buscar(userId, id);
        return ResponseEntity.ok(new EntregaView(entrega));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Lista todas entregas do cliente logado")
    public ResponseEntity<List<EntregaView>> listaEntregas(HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        var entregas = service
                .listar(userId)
                .stream()
                .map(EntregaView::new)
                .toList();

        return ResponseEntity.ok(entregas);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deleta entrega SE não estiver em andamento")
    public void deletaEntrega(@PathVariable Long id,
                              HttpServletRequest request){
        var userId = tokenUtils.getUserId(request);
        service.deletar(userId, id);
    }
}
