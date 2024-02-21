package br.com.acert.api.domain.pedido;

import br.com.acert.api.domain.cliente.Cliente;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "pedidos")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String descricao;

    public Pedido(Cliente cliente, String descricao) {
        this.cliente = cliente;
        this.descricao = descricao;
    }

    public Pedido atualiza (PedidoFormAtualiza form){
        descricao = form.descricao();
        return this;
    }
}


