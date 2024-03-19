package br.com.acert.api.infra.database;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaRepository;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoRepository;
import br.com.acert.api.service.ClienteService;
import br.com.acert.api.service.EntregaService;
import br.com.acert.api.service.PedidoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializationService {
    @Value("${spring.datasource.init:true}")
    private boolean databaseInit;
    private final ClienteRepository clienteRepository;
    private final EntregaRepository entregaRepository;
    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private final EntregaService entregaService;

    @Autowired
    public DatabaseInitializationService(ClienteRepository clienteRepository,
                                         EntregaRepository entregaRepository,
                                         PedidoRepository pedidoRepository,
                                         ClienteService clienteService,
                                         PedidoService pedidoService,
                                         EntregaService entregaService) {
        this.clienteRepository = clienteRepository;
        this.entregaRepository = entregaRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        this.entregaService = entregaService;
    }

    @PostConstruct
    public void inicializaDatabase() {
        if (databaseInit) {
            removeDados();
            criaUsuarioSemEntrega();
        }
    }

    private void removeDados() {
        entregaRepository.deleteAll();
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    private Cliente criaUsuarioSemEntrega() {
        var clienteSalvo = clienteService.criar(new ClienteFormNovo(
                "Melon Husk",
                "user@email.com",
                "123456"));

        var pedidoSalvo = pedidoService.criar(clienteSalvo.getId(), new PedidoFormNovo("pedido teste"));
        entregaService.criar(
                clienteSalvo.getId(),
                new EntregaFormNovo(
                        pedidoSalvo.getId(),
                        "SP",
                        "São Paulo",
                        "12345678",
                        "Bairro Estranho",
                        "rua estranha",
                        42,
                        null));

        return clienteSalvo;
    }
}