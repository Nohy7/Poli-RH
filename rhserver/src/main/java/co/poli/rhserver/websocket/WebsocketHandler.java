package co.poli.rhserver.websocket;

import co.poli.rhserver.model.Empleado;
import co.poli.rhserver.dto.Mensaje;
import co.poli.rhserver.service.EmpleadoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private final EmpleadoService empleadoService;
    private final ObjectMapper mapper;

    @Autowired
    public WebsocketHandler(EmpleadoService empleadoService, ObjectMapper mapper) {
        this.empleadoService = empleadoService;
        this.mapper = mapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Se ha unido el usuario {}", session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Se ha desconectado el usuario {}", session.getId());
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String payload = message.getPayload();
            System.out.println(payload);
            JsonNode jsonNode = mapper.readTree(payload);
            Mensaje mensaje = mapper.treeToValue(jsonNode, Mensaje.class);
            log.info("Usuario {}, acción {}", session.getId(), mensaje.getAccion());

            switch (mensaje.getAccion()) {
                case "CREAR_EMPLEADO" -> crearEmpleado(session, mensaje.getDatos());
                case "CONSULTAR_EMPLEADO" -> consultarEmpleado(session, mensaje.getDatos());
                case "CONSULTAR_EMPLEADOS" -> consultarTodosEmpleados(session);
                case "ACTUALIZAR_EMPLEADO" -> actualizarEmpleado(session, mensaje.getDatos());
                case "ELIMINAR_EMPLEADO" -> eliminarEmpleado(session, mensaje.getDatos());
                default -> log.error("Acción no encontrada");
            }

        } catch (Exception e) {
            log.error("Error ", e);
        }
    }

    private void crearEmpleado(WebSocketSession session, Object datos) throws IOException {
        Empleado nuevoEmpleado = mapper.convertValue(datos, Empleado.class);
        Empleado empleadoGuardado = empleadoService.guardar(nuevoEmpleado);
        enviarRespuesta(session, empleadoGuardado);
    }

    private void consultarEmpleado(WebSocketSession session, Object datos) throws IOException {
        Integer id = Integer.parseInt((String) datos);
        Empleado empleado = empleadoService.consultarPorId(id);
        enviarRespuesta(session, empleado);
    }

    private void consultarTodosEmpleados(WebSocketSession session) throws IOException {
        List<Empleado> empleados = empleadoService.consultarTodos();
        enviarRespuesta(session, empleados);
    }

    private void actualizarEmpleado(WebSocketSession session, Object datos) throws IOException {
        Empleado empleado = mapper.convertValue(datos, Empleado.class);
        Empleado empleadoActualizado = empleadoService.guardar(empleado);
        enviarRespuesta(session, empleadoActualizado);
    }

    private void eliminarEmpleado(WebSocketSession session, Object datos) throws IOException {
        Integer id = Integer.parseInt((String) datos);
        empleadoService.eliminar(id);
        TextMessage textMessage = new TextMessage("Empleado eliminado con éxito");
        session.sendMessage(textMessage);
    }

    private void enviarRespuesta(WebSocketSession session, Object respuesta) throws IOException {
        String responseJson = mapper.writeValueAsString(respuesta);
        TextMessage textMessage = new TextMessage(responseJson);
        session.sendMessage(textMessage);
    }

}
