package co.poli.rhserver.websocket;

import co.poli.rhserver.dto.Mensaje;
import co.poli.rhserver.model.Empleado;
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

@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);
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
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Se ha desconectado el usuario {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        try {
            String payload = message.getPayload();
            JsonNode jsonNode = mapper.readTree(payload);
            Mensaje mensaje = mapper.treeToValue(jsonNode, Mensaje.class);
            log.info("Usuario: {}, acción: {}, datos: {}", session.getId(), mensaje.getAccion(), mensaje.getDatos());

            switch (mensaje.getAccion()) {
                case "CREAR_EMPLEADO" -> crearEmpleado(session, mensaje.getDatos());
                case "CONSULTAR_EMPLEADO" -> consultarEmpleado(session, mensaje.getDatos());
                case "CONSULTAR_EMPLEADOS" -> consultarTodosEmpleados(session);
                case "ACTUALIZAR_EMPLEADO" -> actualizarEmpleado(session, mensaje.getDatos());
                case "ELIMINAR_EMPLEADO" -> eliminarEmpleado(session, mensaje.getDatos());
                default -> throw new WebsocketError("Acción no valida");
            }

        } catch (Exception e) {
            log.error("Error ", e);
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    private void crearEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Empleado nuevoEmpleado = mapper.convertValue(datos, Empleado.class);
            empleadoService.guardar(nuevoEmpleado);
            enviarRespuesta(session, new Mensaje("CREAR_EMPLEADO", "Empleado registrado con éxito"));
        } catch (WebsocketError e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }

    }

    private void consultarEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Integer id = Integer.parseInt((String) datos);
            Empleado empleado = empleadoService.consultarPorId(id);
            enviarRespuesta(session, new Mensaje("CONSULTAR_EMPLEADO", empleado));
        } catch (Exception e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    private void consultarTodosEmpleados(WebSocketSession session) throws IOException {
        try {
            List<Empleado> empleados = empleadoService.consultarTodos();
            enviarRespuesta(session, new Mensaje("CONSULTAR_EMPLEADOS", empleados));
        } catch (Exception e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    private void actualizarEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Empleado empleado = mapper.convertValue(datos, Empleado.class);
            empleadoService.guardar(empleado);
            enviarRespuesta(session, new Mensaje("ACTUALIZAR_EMPLEADO", "Empleado actualizado con éxito"));
        } catch (WebsocketError e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }

    }

    private void eliminarEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Integer id = Integer.parseInt((String) datos);
            empleadoService.eliminar(id);
            enviarRespuesta(session, new Mensaje("ELIMINAR_EMPLEADO", "Empleado eliminado con éxito"));
        } catch (WebsocketError e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    private void enviarRespuesta(WebSocketSession session, Object respuesta) throws IOException {
        String responseJson = mapper.writeValueAsString(respuesta);
        TextMessage textMessage = new TextMessage(responseJson);
        session.sendMessage(textMessage);
    }

}
