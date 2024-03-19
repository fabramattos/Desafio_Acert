package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaRepository;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoRepository;
import br.com.acert.api.infra.database.DatabaseContainerConfig;
import br.com.acert.api.infra.exception.ClienteNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class ClienteServiceTest extends DatabaseContainerConfig {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EntregaRepository entregaRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private EntregaService entregaService;

    private ClienteFormNovo clienteFormNovo;

    @BeforeEach
    void criaClienteForm() {
        clienteFormNovo = new ClienteFormNovo("Xablau", "xablau@email.com", "senhaFraca");
    }

    @Test
    @DisplayName("Dado um ClienteFormNovo válido, deve criar usuário")
    void criar() {
        var clientes = clienteRepository.findAll();
        assertEquals(0, clientes.size(), "Lista deveria ter zero clientes ao iniciar DB");

        var clienteCriado = clienteService.criar(clienteFormNovo);

        var clienteRecuperado = clienteRepository.findById(clienteCriado.getId()).orElseThrow();

        assertEquals(clienteCriado.getNome(), clienteRecuperado.getNome());
        assertEquals(clienteCriado.getLogin(), clienteRecuperado.getLogin());
        assertNotEquals(clienteCriado.getPassword(), clienteRecuperado.getLogin()); //devido a encoder
    }

    @Test
    @DisplayName("Dado um ClienteFormAtualiza válido, deve atualizar e salvar o cadastro")
    void alterarIdAutenticado() {
        var clienteCriado = clienteService.criar(clienteFormNovo);
        var clienteAlterado = clienteService.alterar(clienteCriado.getId(),
                new ClienteFormAtualiza("nome atualizado", "atualizado@email.com", null));

        assertEquals("nome atualizado", clienteAlterado.getNome());
        assertEquals("atualizado@email.com", clienteAlterado.getLogin());

        var clienteBuscado = clienteService.buscar(clienteCriado.getId());
        assertEquals("nome atualizado", clienteBuscado.getNome());
        assertEquals("atualizado@email.com", clienteBuscado.getLogin());

    }


    @Test
    @DisplayName("Dado um userId válido, Quando solicitar deletar cadastro e não houver entregas em andamento," +
            " Deve deletar o cadastro e dados associados")
    void deletar() {
        var clienteCriado = clienteService.criar(new ClienteFormNovo("Xablau", "xablau@email.com", "senhaFraca"));
        var pedido = pedidoService.criar(clienteCriado.getId(), new PedidoFormNovo("descricao"));
        var entrega = entregaService.criar(
                clienteCriado.getId(),
                new EntregaFormNovo(
                        pedido.getId(),
                        "SP",
                        "São Paulo",
                        "11111111",
                        "Bairro Qualquer",
                        "Rua Qualquer",
                        42,
                        null)
                );
        assertEquals(1, clienteRepository.findAll().size());
        assertEquals(1, pedidoRepository.findAll().size());
        assertEquals(1, entregaRepository.findAll().size());

        clienteService.deletar(clienteCriado.getId());

        assertTrue(clienteRepository.findAll().isEmpty());
        assertTrue(pedidoRepository.findAll().isEmpty());
        assertTrue(entregaRepository.findAll().isEmpty());
    }


    @Test
    @DisplayName("Dado um ID, deve retornar o cliente correto e seus dados.")
    void consultar() {
        var clienteCriado = clienteService.criar(clienteFormNovo);
        var clienteRecuperado = clienteService.buscar(clienteCriado.getId());

        assertEquals(clienteCriado.getNome(), clienteRecuperado.getNome());
        assertEquals(clienteCriado.getLogin(), clienteRecuperado.getLogin());
    }

    @Test
    @DisplayName("Dado um ID inexistente, deve lançar exception.")
    void consultar2() {
        assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.buscar(-2L));
    }
}