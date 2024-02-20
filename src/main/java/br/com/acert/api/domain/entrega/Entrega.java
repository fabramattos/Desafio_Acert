package br.com.acert.api.domain.entrega;

import br.com.acert.api.domain.pedido.Pedido;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "entregas")
public class Entrega {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String UF;
    private String cidade;
    private String cep;
    private String bairro;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private EntregaStatus status;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
