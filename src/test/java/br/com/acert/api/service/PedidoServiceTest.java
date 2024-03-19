package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaRepository;
import br.com.acert.api.domain.pedido.PedidoFormAtualiza;
import br.com.acert.api.domain.pedido.PedidoFormNovo;
import br.com.acert.api.domain.pedido.PedidoRepository;
import br.com.acert.api.infra.database.DatabaseContainerConfig;
import br.com.acert.api.infra.exception.PedidoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class PedidoServiceTest extends DatabaseContainerConfig {

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

    private Cliente cliente;

    @BeforeEach
    void criaClienteForm() {
        cliente = clienteService.criar(new ClienteFormNovo("Xablau", "xablau@email.com", "senhaFraca"));
    }

    @Test
    @DisplayName("Dado um PedidoFormNovo válido, deve criar pedido")
    void criar() {
        assertTrue(pedidoRepository.findAll().isEmpty(), "Lista deveria ter zero pedidos ao iniciar DB");

        var pedido = pedidoService.criar(cliente.getId(), new PedidoFormNovo("descrição 1"));

        var pedidoRecuperado = pedidoRepository.findById(pedido.getId()).orElseThrow();

        assertEquals(pedido.getCliente(), cliente);
        assertEquals(pedido.getDescricao(), pedidoRecuperado.getDescricao());
        assertNull(pedido.getEntrega());
        assertEquals(pedido.getDescricao(), pedidoRecuperado.getDescricao());
    }

    @Test
    @DisplayName("Dado um PedidoFormAtualiza válido, deve atualizar e salvar o cadastro")
    void alterarIdAutenticado() {
        var pedido = pedidoService.criar(cliente.getId(), new PedidoFormNovo("descrição 1"));

        var pedidoAtualizado = pedidoRepository
                .findById(pedido.getId())
                .orElseThrow()
                .atualiza(new PedidoFormAtualiza(pedido.getId(),"atualizado"));

        assertEquals("atualizado", pedidoAtualizado.getDescricao());

        var pedidoBuscado = pedidoService.buscar(cliente.getId(), pedido.getId());

        assertEquals("atualizado", pedidoBuscado.getDescricao());

    }

    @Test
    @DisplayName("Dado um pedidoId válido, Quando solicitar deletar pedido e não houver entregas em andamento," +
            " Deve deletar o pedido e entrega associada")
    void deletar() {
        var pedido = pedidoService.criar(cliente.getId(), new PedidoFormNovo("descrição 1"));
        var entrega = entregaService.criar(
                cliente.getId(),
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
        assertEquals(1, pedidoRepository.findAll().size());
        assertEquals(1, entregaRepository.findAll().size());

        pedidoService.deletar(cliente.getId(), pedido.getId());

        assertTrue(pedidoRepository.findAll().isEmpty());
        assertTrue(entregaRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Dado um ID, deve retornar o pedido correto e seus dados.")
    void consultar() {
        var pedido = pedidoService.criar(cliente.getId(), new PedidoFormNovo("pedido 1"));
        var pedidoRecuperado = pedidoService.buscar(cliente.getId(), pedido.getId());

        assertEquals(pedido.getDescricao(), pedidoRecuperado.getDescricao());
        assertEquals(pedido.getEntrega(), pedidoRecuperado.getEntrega());
    }

    @Test
    @DisplayName("Dado um ID inexistente, deve lançar exception.")
    void consultar2() {
        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.buscar(cliente.getId(), -2L));
    }

    @Test
    @DisplayName("Dado um usuarioId inválido para um pedido existente, deve lançar exception.")
    void consultar3() {
        var pedido = pedidoService.criar(cliente.getId(), new PedidoFormNovo("pedido 1"));
        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.buscar(-1L, pedido.getId()));
    }
}