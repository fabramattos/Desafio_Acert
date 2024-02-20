package br.com.acert.api.domain.item;

import br.com.acert.api.domain.pedido.Pedido;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "itens")
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
