package br.com.acert.api.domain.entrega;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public record EntregaFormAtualiza(
        @NotNull
        Long id,
        String UF,
        String cidade,
        @Digits(message = "apenas dígitos aceitos", integer = 8, fraction = 0)
        String cep,
        String bairro,
        String logradouro,
        Integer numero,
        String complemento,
        EntregaStatus statusEntrega
) {
}
