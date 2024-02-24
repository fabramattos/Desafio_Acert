package br.com.acert.api.infra.database;

import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.domain.entrega.EntregaRepository;
import br.com.acert.api.domain.pedido.PedidoRepository;
import br.com.acert.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializationService {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EntregaRepository entregaRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @PostConstruct
    public void inicializaDatabase() {
        inicializaClientes();
    }

    private void inicializaClientes() {
        clienteRepository.deleteAll();
        clienteService.criar(new ClienteFormNovo("Melon Husk", "admin@email.com", "123456"));
        clienteService.criar(new ClienteFormNovo("Reanu Keaves", "user@email.com", "123456"));
    }
}
