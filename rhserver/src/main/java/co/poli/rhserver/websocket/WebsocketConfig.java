package co.poli.rhserver.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuración para habilitar y configurar el soporte WebSocket en la aplicación.
 */
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final WebsocketHandler websocketHandler;

    /**
     * Constructor de la configuración WebSocket.
     *
     * @param websocketHandler Manejador de WebSocket.
     */
    public WebsocketConfig(WebsocketHandler websocketHandler) {
        this.websocketHandler = websocketHandler;
    }

    /**
     * Registra el manejador de WebSocket y establece la configuración.
     *
     * @param registry Registro de manejadores de WebSocket.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Configuración del manejador de WebSocket en el camino "/poli-rh" y se permiten todos los orígenes.
        registry.addHandler(websocketHandler, "/poli-rh").setAllowedOrigins("*");
    }
}