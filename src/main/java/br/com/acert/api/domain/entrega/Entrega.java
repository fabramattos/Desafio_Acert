package br.com.acert.api.domain.entrega;

import br.com.acert.api.domain.pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@Entity(name = "Entrega")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Entrega {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 2)
    private String UF;
    @Column(nullable = false, length = 100)
    private String cidade;
    @Column(nullable = false, length = 100) @Digits(integer = 8, fraction = 0)
    private String cep;
    @Column(nullable = false, length = 100)
    private String bairro;
    @Column(nullable = false, length = 100)
    private String logradouro;
    @Column(nullable = false, length = 10)
    private Integer numero;
    @Column(length = 100)
    private String complemento;
    @Column(nullable = false)
    private EntregaStatus status;

    @OneToOne
    @JsonIgnore
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
        this.status = EntregaStatus.NAO_INICIADA;
    }

    public Entrega atualiza(EntregaFormAtualiza form) {
        if (form.UF() != null && !form.UF().isBlank())
            this.UF = form.UF();
        if (form.cidade() != null && !form.cidade().isBlank())
            this.cidade = form.cidade();
        if (form.cep() != null && !form.cep().isBlank())
            this.cep = form.cep();
        if (form.bairro() != null && !form.bairro().isBlank())
            this.bairro = form.bairro();
        if (form.logradouro() != null && !form.logradouro().isBlank())
            this.logradouro = form.logradouro();
        if (form.numero() != null)
            this.numero = form.numero();
        if (form.complemento() != null && !form.complemento().isBlank())
            this.complemento = form.complemento();
        if (form.statusEntrega() != null)
            this.status = form.statusEntrega();

        return this;
    }
}