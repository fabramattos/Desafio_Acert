package br.com.acert.api.domain.entrega;

public record EntregaViewSimples(
        long idEntrega,
        String UF,
        String cidade,
        String cep,
        String bairro,
        String logradouro,
        int numero,
        String complemento,
        EntregaStatus status
) {

    public EntregaViewSimples(Entrega entrega) {
        this(entrega.getId(),
                entrega.getUF(),
                entrega.getCidade(),
                entrega.getCep(),
                entrega.getBairro(),
                entrega.getLogradouro(),
                entrega.getNumero(),
                entrega.getComplemento(),
                entrega.getStatus()
        );
    }
}
