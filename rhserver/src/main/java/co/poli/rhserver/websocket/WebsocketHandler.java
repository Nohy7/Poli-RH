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

/**
 * Manejador de WebSocket que procesa los mensajes entrantes y realiza operaciones en función de la acción especificada.
 */
@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(WebsocketHandler.class);
    private final EmpleadoService empleadoService;
    private final ObjectMapper mapper;

    /**
     * Constructor que recibe instancias de servicios necesarios.
     *
     * @param empleadoService Servicio para gestionar operaciones relacionadas con empleados.
     * @param mapper          ObjectMapper para convertir entre objetos Java y JSON.
     */
    @Autowired
    public WebsocketHandler(EmpleadoService empleadoService, ObjectMapper mapper) {
        this.empleadoService = empleadoService;
        this.mapper = mapper;
    }

    /**
     * Se invoca cuando se establece una conexión WebSocket.
     *
     * @param session Sesión WebSocket recién establecida.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Se ha unido el usuario {}", session.getId());
    }

    /**
     * Se invoca cuando se cierra una conexión WebSocket.
     *
     * @param session Sesión WebSocket cerrada.
     * @param status  Estado del cierre.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Se ha desconectado el usuario {}", session.getId());
    }

    /**
     * Maneja los mensajes de texto recibidos y realiza operaciones en función de la acción especificada.
     *
     * @param session Sesión WebSocket.
     * @param message Mensaje de texto recibido.
     * @throws IOException Si ocurre un error al procesar el mensaje.
     */
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

    /**
     * Crea un nuevo empleado a partir de los datos proporcionados y lo guarda en la base de datos.
     *
     * @param session Sesión WebSocket.
     * @param datos   Datos del nuevo empleado.
     * @throws IOException Si ocurre un error al procesar el mensaje.
     */
    private void crearEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Empleado nuevoEmpleado = mapper.convertValue(datos, Empleado.class);
            empleadoService.guardar(nuevoEmpleado);
            enviarRespuesta(session, new Mensaje("CREAR_EMPLEADO", "Empleado registrado con éxito"));
        } catch (WebsocketError e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }

    }

    /**
     * Consulta un empleado por su ID y envía la respuesta a través de la sesión WebSocket.
     *
     * @param session Sesión WebSocket.
     * @param datos   ID del empleado a consultar.
     * @throws IOException Si ocurre un error al procesar el mensaje.
     */
    private void consultarEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Integer id = Integer.parseInt((String) datos);
            Empleado empleado = empleadoService.consultarPorId(id);
            enviarRespuesta(session, new Mensaje("CONSULTAR_EMPLEADO", empleado));
        } catch (Exception e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    /**
     * Consulta todos los empleados y envía la respuesta a través de la sesión WebSocket.
     *
     * @param session Sesión WebSocket.
     * @throws IOException Si ocurre un error al procesar el mensaje.
     */
    private void consultarTodosEmpleados(WebSocketSession session) throws IOException {
        try {
            List<Empleado> empleados = empleadoService.consultarTodos();
            enviarRespuesta(session, new Mensaje("CONSULTAR_EMPLEADOS", empleados));
        } catch (Exception e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    /**
     * Actualiza un empleado a partir de los datos proporcionados y lo guarda en la base de datos.
     *
     * @param session Sesión WebSocket.
     * @param datos   Datos del empleado a actualizar.
     * @throws IOException Si ocurre un error al procesar el mensaje.
     */
    private void actualizarEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Empleado empleado = mapper.convertValue(datos, Empleado.class);
            empleadoService.guardar(empleado);
            enviarRespuesta(session, new Mensaje("ACTUALIZAR_EMPLEADO", "Empleado actualizado con éxito"));
        } catch (WebsocketError e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }

    }

    /**
     * Elimina un empleado a partir de su ID, lo marca como inactivo y guarda la información en el histórico.
     *
     * @param session Sesión WebSocket.
     * @param datos   ID del empleado a eliminar.
     * @throws IOException Si ocurre un error al procesar el mensaje.
     */
    private void eliminarEmpleado(WebSocketSession session, Object datos) throws IOException {
        try {
            Integer id = Integer.parseInt((String) datos);
            empleadoService.eliminar(id);
            enviarRespuesta(session, new Mensaje("ELIMINAR_EMPLEADO", "Empleado eliminado con éxito"));
        } catch (WebsocketError e) {
            enviarRespuesta(session, new Mensaje("ERROR", e.getMessage()));
        }
    }

    /**
     * Envía una respuesta al cliente a través de la sesión WebSocket.
     *
     * @param session   Sesión WebSocket.
     * @param respuesta Objeto que se convertirá a JSON y se enviará como respuesta.
     * @throws IOException Si ocurre un error al enviar la respuesta.
     */
    private void enviarRespuesta(WebSocketSession session, Object respuesta) throws IOException {
        String responseJson = mapper.writeValueAsString(respuesta);
        TextMessage textMessage = new TextMessage(responseJson);
        session.sendMessage(textMessage);
    }
}
