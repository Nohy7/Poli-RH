package co.poli.rhserver.dto;

/**
 * Representa un mensaje enviado a través del sistema.
 * Contiene información sobre la acción a realizar y los datos asociados.
 */
public class Mensaje {

    /**
     * Acción que se debe realizar en base al mensaje.
     */
    private String accion;

    /**
     * Datos asociados al mensaje. Puede ser de cualquier tipo.
     */
    private Object datos;

    /**
     * Constructor predeterminado de la clase Mensaje.
     * Inicializa una instancia de Mensaje sin valores específicos.
     */
    public Mensaje() {

    }

    /**
     * Constructor de la clase Mensaje con parámetros.
     *
     * @param accion Acción a realizar en base al mensaje.
     * @param datos Datos asociados al mensaje. Puede ser de cualquier tipo.
     */
    public Mensaje(String accion, Object datos) {
        this.accion = accion;
        this.datos = datos;
    }

    /**
     * Obtiene la acción asociada al mensaje.
     *
     * @return Acción a realizar en base al mensaje.
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Establece la acción asociada al mensaje.
     *
     * @param accion Acción a realizar en base al mensaje.
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * Obtiene los datos asociados al mensaje.
     *
     * @return Datos asociados al mensaje. Puede ser de cualquier tipo.
     */
    public Object getDatos() {
        return datos;
    }

    /**
     * Establece los datos asociados al mensaje.
     *
     * @param datos Datos asociados al mensaje. Puede ser de cualquier tipo.
     */
    public void setDatos(Object datos) {
        this.datos = datos;
    }
}

