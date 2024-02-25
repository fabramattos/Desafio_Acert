package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import br.com.acert.api.infra.exception.ClienteNaoEncontradoException;
import br.com.acert.api.infra.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements UserDetailsService {

    @Autowired
    ClienteRepository repository;
    @Autowired
    EntregaService entregaService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findByLogin(username)
                .orElseThrow(LoginException::new);
    }

    public Cliente criar(ClienteFormNovo form) {
        var formCodificado = new ClienteFormNovo(form.nome(), form.login(), encoder.encode(form.senha()));
        return repository.save(new Cliente(formCodificado));
    }

    public Cliente alterarIdAutenticado(ClienteFormAtualiza form) {
        return consultarIdAutenticado()
                .atualiza(form);
    }

    public void deletarIdAutenticado() {
        var cliente = consultarIdAutenticado();
        var pedidos = cliente.getPedidos();

        pedidos.forEach(pedido -> {
            var entrega = pedido.getEntrega();
            entregaService.verificaStatusEntrega(entrega);
        });

        repository.delete(cliente);
    }

    public Cliente consultarIdAutenticado() {
        return repository
                .findById(tokenService.idUsuarioAutenticado())
                .orElseThrow(ClienteNaoEncontradoException::new);
    }
}
