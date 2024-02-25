package br.com.acert.api.service;

import br.com.acert.api.domain.entrega.*;
import br.com.acert.api.infra.exception.EntregaEmAndamentoException;
import br.com.acert.api.infra.exception.EntregaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

    @Autowired
    EntregaRepository repository;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    TokenService tokenService;

    public Entrega criar(EntregaFormNovo form) {
        var pedido = pedidoService.consultar(form.pedidoId());
        return repository.save(new Entrega(pedido, form));
    }

    public Entrega atualizar(EntregaFormAtualiza form) {
        var entrega = tentaBuscarEntrega(form.id());
        return entrega.atualiza(form);
    }

    public Entrega buscar(Long id) {
        return tentaBuscarEntrega(id);
    }

    public void deletar(Long id) {
        var entrega = tentaBuscarEntrega(id);
        repository.delete(entrega);
    }

    public List<Entrega> listar() {
        var userIdAutenticado = tokenService.idUsuarioAutenticado();
        return repository
                .findAll()
                .stream()
                .filter(entrega ->
                        entrega.getPedido().getCliente().getId() == userIdAutenticado)
                .toList();
    }

    private Entrega tentaBuscarEntrega(Long id) {
        var entrega = repository
                .findById(id)
                .orElseThrow(EntregaNaoEncontradaException::new);

        tokenService.comparaComUserIdAutenticado(entrega.getPedido().getCliente().getId(), new EntregaNaoEncontradaException());
        return entrega;
    }

    protected void verificaStatusEntrega(Entrega entrega) {
        if(entrega != null && entrega.getStatus() != EntregaStatus.NAO_INICIADA)
            throw new EntregaEmAndamentoException();
    }
}
