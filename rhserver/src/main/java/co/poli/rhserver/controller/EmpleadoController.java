package co.poli.rhserver.controller;

import co.poli.rhserver.model.Empleado;
import co.poli.rhserver.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Empleado> guardarEmpleado(@RequestBody Empleado empleado) {
        Empleado empleadoGuardado = service.guardar(empleado);
        return new ResponseEntity<>(empleadoGuardado, HttpStatus.CREATED);
    }

    @GetMapping("/consultarTodos")
    public ResponseEntity<List<Empleado>> consultarTodosEmpleados() {
        List<Empleado> empleados = service.consultarTodos();
        return new ResponseEntity<>(empleados, HttpStatus.OK);
    }

    @GetMapping("/consultarPorId/{id}")
    public ResponseEntity<Empleado> consultarEmpleadoPorId(@PathVariable Integer id) {
        Empleado empleado = service.consultarPorId(id);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

