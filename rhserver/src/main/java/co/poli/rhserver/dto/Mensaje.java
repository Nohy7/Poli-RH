package co.poli.rhserver.dto;

public class Mensaje {

    private String accion;
    private Object datos;

    public Mensaje() {

    }

    public Mensaje(String accion, String datos) {
        this.accion = accion;
        this.datos = datos;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}
