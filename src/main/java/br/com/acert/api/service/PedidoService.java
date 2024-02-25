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

    @Autowired
    TokenService tokenService;

    public Pedido criar(PedidoFormNovo form) {
        var cliente = clienteService.consultarIdAutenticado();
        var pedido = new Pedido(cliente, form.descricao());
        cliente.getPedidos().add(pedido);

        return repository.save(pedido);
    }

    public Pedido alterar(PedidoFormAtualiza form) {
        var pedido = tentaBuscarPedido(form.pedidoId());
        return pedido.atualiza(form);
    }

    public Pedido consultar(Long id) {
        return tentaBuscarPedido(id);
    }

    public void deletar(Long id) {
        var pedido = tentaBuscarPedido(id);
        repository.delete(pedido);
    }

    private Pedido tentaBuscarPedido(Long id) {
        var pedido = repository
                .findById(id)
                .orElseThrow();

        tokenService.comparaComUserIdAutenticado(pedido.getCliente().getId());

        return pedido;
    }

    //TODO implementar paginação
    public List<Pedido> listar() {
        var userIdAutenticado = tokenService.idUsuarioAutenticado();
        return repository
                .findAll()
                .stream()
                .filter(pedido -> pedido.getCliente().getId() == userIdAutenticado)
                .toList();
    }
}
