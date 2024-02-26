package br.com.acert.api.service;

import br.com.acert.api.domain.entrega.Entrega;
import br.com.acert.api.domain.entrega.EntregaStatus;
import br.com.acert.api.infra.exception.EntregaEmAndamentoException;
import org.springframework.stereotype.Component;

@Component
public class EntregaUtils {

    protected void verificaStatusEntrega(Entrega entrega) {
        if(entrega != null && entrega.getStatus() != EntregaStatus.NAO_INICIADA)
            throw new EntregaEmAndamentoException();
    }
}
