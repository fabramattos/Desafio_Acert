package br.com.acert.api.service;

import br.com.acert.api.domain.entrega.Entrega;
import br.com.acert.api.domain.entrega.EntregaFormAtualiza;
import br.com.acert.api.domain.entrega.EntregaFormNovo;
import br.com.acert.api.domain.entrega.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

    @Autowired
    EntregaRepository repository;

    @Autowired
    PedidoService pedidoService;

    public Entrega criar(EntregaFormNovo form){
        var pedido = pedidoService.consultar(form.pedidoId());

        return repository.save(new Entrega(pedido, form));
    }

    public Entrega atualizar(EntregaFormAtualiza form){
        return repository
                .findById(form.id())
                .orElseThrow()
                .atualiza(form);
    }

    public Entrega detalhar(Long id){
        return repository
                .findById(id)
                .orElseThrow();
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }

    public List<Entrega> listar() {
        return repository.findAll();
    }
}
