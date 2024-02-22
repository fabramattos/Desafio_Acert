package br.com.acert.api.domain.entrega;

import br.com.acert.api.domain.pedido.Pedido;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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

    public Entrega(Pedido pedido, EntregaFormNovo form) {
        this.pedido = pedido;
        this.UF = form.UF();
        this.cidade = form.cidade();
        this.cep = form.cep();
        this.bairro = form.bairro();
        this.logradouro = form.logradouro();
        this.numero = form.numero();
        this.complemento = form.complemento();
        this.status = EntregaStatus.SEM_INFORMACAO;
    }

    public Entrega atualiza(EntregaFormAtualiza form) {
        if (!form.UF().isBlank())
            this.UF = form.UF();
        if (!form.cidade().isBlank())
            this.cidade = form.cidade();
        if (!form.cep().isBlank())
            this.cep = form.cep();
        if (!form.bairro().isBlank())
            this.bairro = form.bairro();
        if (!form.logradouro().isBlank())
            this.logradouro = form.logradouro();
        if (form.numero() != null)
            this.numero = form.numero();
        if (!form.complemento().isBlank())
            this.complemento = form.complemento();
        if (form.statusEntrega() != null)
            this.status = EntregaStatus.SEM_INFORMACAO;

        return this;
    }
}