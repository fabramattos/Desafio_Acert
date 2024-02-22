package br.com.acert.api.service;

import br.com.acert.api.domain.pedido.Pedido;
import br.com.acert.api.domain.pedido.PedidoFormAtualiza;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    ClienteService clienteService;

    @Autowired
    PedidoRepository repository;

    public Pedido criar(PedidoFormNovo form) {
        var cliente = clienteService.consultar(form.clientId());
        var pedido = new Pedido(cliente, form.descricao());

        return repository.save(pedido);
    }

    public Pedido alterar(PedidoFormAtualiza form) {
        return repository
                .findById(form.pedidoId())
                .orElseThrow()
                .atualiza(form);
    }

    public Pedido consultar(Long id) {
        return repository
                .findById(id)
                .orElseThrow();
    }

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
