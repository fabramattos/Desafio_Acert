package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.infra.exception.ClienteNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ClienteService implements UserDetailsService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EntregaUtils entregaUtils;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findByLogin(username)
                .orElseThrow();
    }

    @Transactional
    public Cliente criar(ClienteFormNovo form) {
        var formCodificado = new ClienteFormNovo(form.nome(), form.login(), encoder.encode(form.senha()));
        return repository.save(new Cliente(formCodificado));
    }

    @Transactional
    public Cliente alterar(Long userId, ClienteFormAtualiza form) {
        return buscar(userId)
                .atualiza(form);
    }

    @Transactional
    public void deletar(Long userId) {
        var cliente = buscar(userId);
        var pedidos = cliente.getPedidos();

        pedidos.forEach(pedido -> {
            var entrega = pedido.getEntrega();
            entregaUtils.verificaStatusEntrega(entrega);
        });

        repository.deleteById(userId);
    }

    public Cliente buscar(Long userId) {
        return repository
                .findById(userId)
                .orElseThrow(ClienteNaoEncontradoException::new);
    }
}
