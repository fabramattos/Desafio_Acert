package br.com.acert.api.service;

import br.com.acert.api.domain.entrega.Entrega;
import br.com.acert.api.domain.entrega.EntregaFormAtualiza;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaRepository;
import br.com.acert.api.infra.exception.EntregaExistenteException;
import br.com.acert.api.infra.exception.EntregaNaoEncontradaException;
import jakarta.transaction.Transactional;
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
    EntregaUtils entregaUtils;

    @Transactional
    public Entrega criar(Long userId, EntregaFormNovo form) {
        var pedido = pedidoService.buscar(userId, form.pedidoId());
        if(pedido.getEntrega() != null)
            throw new EntregaExistenteException();

        var entrega = new Entrega(pedido, form);
        pedido.setEntrega(entrega);

        return repository.save(entrega);
    }

    @Transactional
    public Entrega atualizar(Long userId, EntregaFormAtualiza form) {
        var entrega = buscar(userId, form.id());
        return entrega.atualiza(form);
    }

    @Transactional
    public void deletar(Long userId, Long entregaId) {
        var entrega = buscar(userId, entregaId);
        entregaUtils.verificaStatusEntrega(entrega);
        entrega.getPedido().setEntrega(null);
        repository.delete(entrega);
    }

    public List<Entrega> listar(Long userId) {
        return repository
                .findAll()
                .stream()
                .filter(entrega ->
                        entrega.getPedido().getCliente().getId() == userId)
                .toList();
    }

    public Entrega buscar(Long userId, Long entregaId) {
        var entrega = repository
                .findById(entregaId)
                .orElseThrow(EntregaNaoEncontradaException::new);

        if(entrega.getPedido().getCliente().getId() != userId)
            throw new EntregaNaoEncontradaException();

        return entrega;
    }

}
