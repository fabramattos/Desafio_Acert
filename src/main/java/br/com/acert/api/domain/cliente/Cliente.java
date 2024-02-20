package br.com.acert.api.domain.cliente;

import br.com.acert.api.domain.pedido.Pedido;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "clientes")
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String login;
    private String senha;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    public Cliente(FormNovoCliente form) {
        nome = form.nome();
        login = form.login();
        senha = form.senha(); //TODO usar encode de senha antes de gravar no banco
    }

    public Cliente atualiza(FormAtualizaCliente form){
        if(!form.nome().isBlank())
            nome = form.nome();
        if(!form.login().isBlank())
            login = form.login();
        if(!form.senha().isBlank())
            senha = form.senha();

        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
