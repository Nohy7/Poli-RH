package co.poli.rhserver.websocket;

/**
 * Excepción personalizada para manejar errores específicos de WebSocket.
 */
public class WebsocketError extends Exception {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message Mensaje de error descriptivo.
     */
    public WebsocketError(String message) {
        super(message);
    }
}

