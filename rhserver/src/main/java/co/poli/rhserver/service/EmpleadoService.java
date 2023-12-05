package co.poli.rhserver.service;

import co.poli.rhserver.model.Empleado;
import co.poli.rhserver.model.Historico;
import co.poli.rhserver.repository.EmpleadoRepository;
import co.poli.rhserver.websocket.WebsocketError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Servicio que proporciona operaciones de negocio relacionadas con la entidad Empleado.
 */
@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;
    private final HistoricoService historicoService;

    /**
     * Constructor del servicio Empleado.
     *
     * @param repository       Repositorio de empleados.
     * @param historicoService Servicio histórico para gestionar historiales de empleados.
     */
    @Autowired
    public EmpleadoService(EmpleadoRepository repository, HistoricoService historicoService) {
        this.repository = repository;
        this.historicoService = historicoService;
    }

    /**
     * Guarda un nuevo empleado.
     *
     * @param empleado Empleado a guardar.
     * @return Empleado guardado.
     * @throws WebsocketError Si ocurre un error durante la validación o si ya existe un empleado con el mismo documento.
     */
    public Empleado guardar(Empleado empleado) throws WebsocketError {
        validarEmpleado(empleado);
        Empleado empleadoBd = repository.findByID(empleado.getEmplId());
        if (empleadoBd != null) {
            throw new WebsocketError("Ya existe un usuario con ese documento");
        }
        return repository.save(empleado);
    }

    /**
     * Consulta todos los empleados activos.
     *
     * @return Lista de empleados activos.
     */
    public List<Empleado> consultarTodos() {
        return repository.findAll();
    }

    /**
     * Consulta un empleado por su ID.
     *
     * @param id ID del empleado a consultar.
     * @return Empleado encontrado.
     * @throws WebsocketError Si el empleado no es encontrado.
     */
    public Empleado consultarPorId(Integer id) throws WebsocketError {
        Empleado empleado = repository.findByID(id);
        if (empleado == null) {
            throw new WebsocketError("El registro no fue encontrado");
        }
        return empleado;
    }

    /**
     * Elimina un empleado por su ID.
     *
     * @param id ID del empleado a eliminar.
     * @throws WebsocketError Si el empleado no es encontrado.
     */
    @Transactional
    public void eliminar(Integer id) throws WebsocketError {
        Empleado empleado = consultarPorId(id);
        empleado.setEmplActivo(false);
        repository.save(empleado);

        Historico historico = new Historico();
        historico.setEmphistId(empleado.getEmplId());
        historico.setEmphistFechaRetiro(new Date());
        historico.setEmphistCargoId(empleado.getEmplCargoId());
        historico.setEmphistDptoId(empleado.getEmplDptoId());
        historicoService.guardar(historico);
    }

    /**
     * Valida los campos obligatorios de un empleado.
     *
     * @param empleado Empleado a validar.
     * @throws WebsocketError Si algún campo obligatorio es nulo.
     */
    public void validarEmpleado(Empleado empleado) throws WebsocketError {
        if (empleado.getEmplId() == null) {
            throw new WebsocketError("El id del empleado no puede ser nulo");
        }
        if (empleado.getEmplPrimerNombre() == null) {
            throw new WebsocketError("El primer nombre del empleado no puede ser nulo");
        }
        if (empleado.getEmplSegundoNombre() == null) {
            throw new WebsocketError("El primer segundo del empleado no puede ser nulo");
        }
        if (empleado.getEmplEmail() == null) {
            throw new WebsocketError("El correo del empleado no puede ser nulo");
        }
        if (empleado.getEmplFechaNac() == null) {
            throw new WebsocketError("La fecha de nacimiento del empleado no puede ser nula");
        }
        if (empleado.getEmplSueldo() == null) {
            throw new WebsocketError("El sueldo del empleado no puede ser nulo");
        }
        if (empleado.getEmplCargoId() == null) {
            throw new WebsocketError("El cargo del empleado no puede ser nulo");
        }
        if (empleado.getEmplDptoId() == null) {
            throw new WebsocketError("El departamento del empleado no puede ser nulo");
        }
    }
}
