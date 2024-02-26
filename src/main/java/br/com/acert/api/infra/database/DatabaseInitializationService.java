package br.com.acert.api.infra.database;

import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.domain.entrega.EntregaFormAtualiza;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaRepository;
import br.com.acert.api.domain.entrega.EntregaStatus;
import br.com.acert.api.domain.pedido.PedidoRepository;
import br.com.acert.api.service.ClienteService;
import br.com.acert.api.service.EntregaService;
import br.com.acert.api.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializationService {

    private ClienteRepository clienteRepository;
    private EntregaRepository entregaRepository;
    private PedidoRepository pedidoRepository;
    private ClienteService clienteService;
    private PedidoService pedidoService;
    private EntregaService entregaService;

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
        removeDados();
        preencheDados();
    }

    private void removeDados() {
        entregaRepository.deleteAll();
        pedidoRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    private void preencheDados() {
        var clienteSalvo = clienteService.criar(new ClienteFormNovo(
                "Melon Husk",
                "user@email.com",
                "123456"));

        var pedidoSalvo = pedidoService.criarDuranteInicializacaoDoBanco(clienteSalvo, "pedido teste");
        entregaService.criarDuranteInicializacaoDoBanco(
                pedidoSalvo,
                new EntregaFormNovo(
                        pedidoSalvo.getId(),
                        "SP",
                        "São Paulo",
                        "12345678",
                        "Bairro Estranho",
                        "rua estranha",
                        42,
                        null));

        var pedidoDoisSalvo = pedidoService.criarDuranteInicializacaoDoBanco(clienteSalvo,"pedido com entrega em andamento");
        var entregaDoisSalva = entregaService.criarDuranteInicializacaoDoBanco(
                pedidoDoisSalvo,
                new EntregaFormNovo(
                pedidoDoisSalvo.getId(),
                "SP",
                "São Paulo",
                "12345678",
                "Bairro Estranho",
                "rua estranha",
                42,
                null));

        var entregaAlterada = entregaDoisSalva.atualiza(new EntregaFormAtualiza(
                entregaDoisSalva.getId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                EntregaStatus.A_CAMINHO));
        entregaRepository.save(entregaAlterada);
    }
}