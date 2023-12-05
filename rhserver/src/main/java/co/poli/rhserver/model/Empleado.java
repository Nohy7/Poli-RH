package co.poli.rhserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

/**
 * Entidad que representa a un empleado en la base de datos.
 */
@Entity
@Table(name = "empleados")
public class Empleado {

    /**
     * Identificador único del empleado.
     */
    @Id
    private Integer emplId;

    /**
     * Primer nombre del empleado.
     */
    private String emplPrimerNombre;

    /**
     * Segundo nombre del empleado.
     */
    private String emplSegundoNombre;

    /**
     * Dirección de correo electrónico del empleado.
     */
    private String emplEmail;

    /**
     * Fecha de nacimiento del empleado.
     */
    private Date emplFechaNac;

    /**
     * Sueldo del empleado.
     */
    private Integer emplSueldo;

    /**
     * Comisión del empleado.
     */
    private Integer emplComision;

    /**
     * Identificador del cargo del empleado.
     */
    private Integer emplCargoId;

    /**
     * Identificador del gerente del empleado.
     */
    private Integer emplGerenteId;

    /**
     * Identificador del departamento del empleado.
     */
    private Integer emplDptoId;

    /**
     * Estado de activación del empleado.
     */
    private Boolean emplActivo;

    public Integer getEmplId() {
        return emplId;
    }

    public void setEmplId(Integer emplId) {
        this.emplId = emplId;
    }

    public String getEmplPrimerNombre() {
        return emplPrimerNombre;
    }

    public void setEmplPrimerNombre(String emplPrimerNombre) {
        this.emplPrimerNombre = emplPrimerNombre;
    }

    public String getEmplSegundoNombre() {
        return emplSegundoNombre;
    }

    public void setEmplSegundoNombre(String emplSegundoNombre) {
        this.emplSegundoNombre = emplSegundoNombre;
    }

    public String getEmplEmail() {
        return emplEmail;
    }

    public void setEmplEmail(String emplEmail) {
        this.emplEmail = emplEmail;
    }

    public Date getEmplFechaNac() {
        return emplFechaNac;
    }

    public void setEmplFechaNac(Date emplFechaNac) {
        this.emplFechaNac = emplFechaNac;
    }

    public Integer getEmplSueldo() {
        return emplSueldo;
    }

    public void setEmplSueldo(Integer emplSueldo) {
        this.emplSueldo = emplSueldo;
    }

    public Integer getEmplComision() {
        return emplComision;
    }

    public void setEmplComision(Integer emplComision) {
        this.emplComision = emplComision;
    }

    public Integer getEmplCargoId() {
        return emplCargoId;
    }

    public void setEmplCargoId(Integer emplCargoId) {
        this.emplCargoId = emplCargoId;
    }

    public Integer getEmplGerenteId() {
        return emplGerenteId;
    }

    public void setEmplGerenteId(Integer emplGerenteId) {
        this.emplGerenteId = emplGerenteId;
    }

    public Integer getEmplDptoId() {
        return emplDptoId;
    }

    public void setEmplDptoId(Integer emplDptoId) {
        this.emplDptoId = emplDptoId;
    }

    public Boolean getEmplActivo() {
        return emplActivo;
    }

    public void setEmplActivo(Boolean emplActivo) {
        this.emplActivo = emplActivo;
    }
}
