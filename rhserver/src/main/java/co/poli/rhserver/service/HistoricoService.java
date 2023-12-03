package co.poli.rhserver.service;

import co.poli.rhserver.model.Historico;
import co.poli.rhserver.repository.HisoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoricoService {

    private final HisoricoRepository repository;

    @Autowired
    public HistoricoService(HisoricoRepository repository) {
        this.repository = repository;
    }
    public Historico guardar(Historico historico) {
        return repository.save(historico);
    }
}
