package br.com.acert.api.domain.pedido;

import br.com.acert.api.domain.cliente.Cliente;
import br.com.acert.api.domain.item.Item;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<Item> itens;
}
