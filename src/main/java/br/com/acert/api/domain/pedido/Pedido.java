package br.com.acert.api.domain.pedido;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.entrega.Entrega;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "Pedido")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;

    @Column(nullable = false)
    private String descricao;


    public Pedido(Cliente cliente, String descricao) {
        this.descricao = descricao;
        this.cliente = cliente;
    }

    public Pedido atualiza (PedidoFormAtualiza form){
        descricao = form.descricao();
        return this;
    }

    public Pedido setEntrega(Entrega entrega){
        this.entrega = entrega;
        return this;
    }
}


