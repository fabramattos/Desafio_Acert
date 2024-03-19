package br.com.acert.api.service;

import br.com.acert.api.domain.pedido.Pedido;
import br.com.acert.api.domain.pedido.PedidoFormAtualiza;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoRepository;
import br.com.acert.api.infra.exception.PedidoNaoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    ClienteService clienteService;

    @Autowired
    EntregaUtils entregaUtils;

    @Autowired
    PedidoRepository repository;

    @Transactional
    public Pedido criar(Long userId, PedidoFormNovo form) {
        var cliente = clienteService.buscar(userId);
        var pedido = new Pedido(cliente, form.descricao());
        cliente.getPedidos().add(pedido);

        return repository.save(pedido);
    }

    @Transactional
    public Pedido alterar(Long userId, PedidoFormAtualiza form) {
        var pedido = buscar(userId, form.pedidoId());
        entregaUtils.verificaStatusEntrega(pedido.getEntrega());
        return pedido.atualiza(form);
    }


    @Transactional
    public void deletar(Long userId, Long pedidoId) {
        var pedido = buscar(userId, pedidoId);
        entregaUtils.verificaStatusEntrega(pedido.getEntrega());

        pedido.getCliente().getPedidos().remove(pedido);
        repository.delete(pedido);
    }

    public Pedido buscar(Long userId, Long pedidoId) {
        var pedido = repository
                .findById(pedidoId)
                .orElseThrow(PedidoNaoEncontradoException::new);

        if(pedido.getCliente().getId() != userId)
            throw new PedidoNaoEncontradoException();

        return pedido;

    }

    //TODO implementar paginação
    public List<Pedido> listar(Long userId) {
        return repository
                .findAll()
                .stream()
                .filter(pedido -> pedido.getCliente().getId() == userId)
                .toList();
    }
}
