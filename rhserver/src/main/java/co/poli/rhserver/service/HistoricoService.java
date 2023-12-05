package co.poli.rhserver.service;

import co.poli.rhserver.model.Historico;
import co.poli.rhserver.repository.HisoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que proporciona operaciones de negocio relacionadas con la entidad Historico.
 */
@Service
public class HistoricoService {

    private final HisoricoRepository repository;

    /**
     * Constructor del servicio Historico.
     *
     * @param repository Repositorio de historiales.
     */
    @Autowired
    public HistoricoService(HisoricoRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda un nuevo historial.
     *
     * @param historico Historial a guardar.
     * @return Historial guardado.
     */
    public Historico guardar(Historico historico) {
        return repository.save(historico);
    }
}
