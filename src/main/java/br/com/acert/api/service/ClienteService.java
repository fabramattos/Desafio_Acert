package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements UserDetailsService {

    @Autowired
    ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public Cliente criar(ClienteFormNovo form) {
        return repository.save(new Cliente(form));
    }

    public Cliente alterar(ClienteFormAtualiza form) {
        return repository
                .findById(form.id())
                .orElseThrow()
                .atualiza(form);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Cliente consultar(Long id) {
        return repository
                .findById(id)
                .orElseThrow();
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

}
