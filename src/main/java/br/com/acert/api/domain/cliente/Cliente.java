package br.com.acert.api.domain.cliente;

import br.com.acert.api.domain.pedido.Pedido;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Cliente")
@Getter
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nome;
    @NotNull
    private String login;
    @NotNull
    private String senha;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();

    //Necessário para spring jpa gerar Objeto a partir do DB
    public Cliente() {
    }

    public Cliente(ClienteFormNovo form) {
        nome = form.nome();
        login = form.login();
        senha = form.senha();
    }

    public Cliente atualiza(ClienteFormAtualiza form) {
        if (!form.nome().isBlank())
            nome = form.nome();
        if (!form.login().isBlank())
            login = form.login();
        if (!form.senha().isBlank())
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
