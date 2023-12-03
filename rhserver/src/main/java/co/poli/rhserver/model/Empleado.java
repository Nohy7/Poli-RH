package co.poli.rhserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    private Integer emplId;
    private String emplPrimerNombre;
    private String emplSegundoNombre;
    private String emplEmail;
    private Date emplFechaNac;
    private Integer emplSueldo;
    private Integer emplComision;
    private Integer emplCargoId;
    private Integer emplGerenteId;
    private Integer emplDptoId;

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
