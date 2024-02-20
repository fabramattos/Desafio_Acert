package br.com.acert.api.service;

import br.com.acert.api.domain.cliente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements UserDetailsService {

    @Autowired
    ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public ViewCliente criar(FormNovoCliente form){
        var cliente = new Cliente(form);
        return new ViewCliente(repository.save(cliente));
    }

    public ViewCliente alterar(FormAtualizaCliente form){
        var cliente =  buscaPorId(form.id());
        return new ViewCliente(cliente.atualiza(form));
    }

    public void deletar(Long id){
        var cliente = buscaPorId(id);
        repository.delete(cliente);
    }

    public ViewCliente consultar(Long id){
        var cliente = buscaPorId(id);
        return new ViewCliente(cliente);
    }

    private Cliente buscaPorId(Long id){
        return repository.findById(id).orElseThrow();
    }

}
