package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.cliente.ClienteFormAtualiza;
import br.com.acert.api.domain.cliente.ClienteFormNovo;
import br.com.acert.api.domain.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findByLogin(username)
                .orElseThrow();
    }

    public Cliente criar(ClienteFormNovo form) {
        var formCodificado = new ClienteFormNovo(form.nome(), form.login(), encoder.encode(form.senha()));
        return repository.save(new Cliente(formCodificado));
    }

    public Cliente alterarIdAutenticado(ClienteFormAtualiza form) {
        return repository
                .findById(tokenService.idUsuarioAutenticado())
                .orElseThrow()
                .atualiza(form);
    }

    public void deletarIdAutenticado() {
        repository.deleteById(tokenService.idUsuarioAutenticado());
    }

    public Cliente consultarIdAutenticado() {
        return repository
                .findById(tokenService.idUsuarioAutenticado())
                .orElseThrow();
    }

    @Secured("ROLE_ADMIN")
    public List<Cliente> listar() {
        return repository.findAll();
    }
}
