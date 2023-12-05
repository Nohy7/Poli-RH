package co.poli.rhserver.repository;

import co.poli.rhserver.model.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder y gestionar entidades de empleados en la base de datos.
 */
@Repository
public interface EmpleadoRepository extends CrudRepository<Empleado, Integer> {

    /**
     * Recupera todos los empleados activos.
     *
     * @return Lista de empleados activos.
     */
    @Query(value = "SELECT * FROM empleados WHERE empl_activo = true", nativeQuery = true)
    List<Empleado> findAll();

    /**
     * Busca un empleado activo por su identificador único.
     *
     * @param id Identificador único del empleado.
     * @return El empleado activo con el identificador dado.
     */
    @Query(value = "SELECT * FROM empleados WHERE empl_activo = true AND empl_id = :id", nativeQuery = true)
    Empleado findByID(Integer id);
}
