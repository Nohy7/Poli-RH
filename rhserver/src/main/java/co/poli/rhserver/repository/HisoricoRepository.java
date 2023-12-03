package co.poli.rhserver.repository;

import co.poli.rhserver.model.Historico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HisoricoRepository extends CrudRepository<Historico, Integer> {
}
