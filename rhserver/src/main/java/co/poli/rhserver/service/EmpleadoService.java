package co.poli.rhserver.service;

import co.poli.rhserver.model.Empleado;
import co.poli.rhserver.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    @Autowired
    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public Empleado guardar(Empleado empleado) {
        return repository.save(empleado);
    }

    public List<Empleado> consultarTodos() {
        return repository.findAll();
    }

    public Empleado consultarPorId(Integer id) {
        return repository.findById(id).get();
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
