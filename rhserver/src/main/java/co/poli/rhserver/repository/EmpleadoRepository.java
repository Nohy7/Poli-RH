package co.poli.rhserver.repository;

import co.poli.rhserver.model.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {

    @Query(value = "SELECT * FROM empleados WHERE empl_activo = true", nativeQuery = true)
    List<Empleado> findAll();

    @Query(value = "SELECT * FROM empleados WHERE empl_activo = true AND empl_id = :id", nativeQuery = true)
    Empleado findByID(Integer id);
}
