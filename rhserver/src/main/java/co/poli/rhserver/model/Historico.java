package co.poli.rhserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

/**
 * Entidad que representa el histórico de un empleado, incluyendo detalles sobre su retiro.
 */
@Entity
@Table(name = "historico")
public class Historico {

    /**
     * Identificador único del histórico.
     */
    @Id
    private Integer emphistId;

    /**
     * Fecha de retiro del empleado.
     */
    private Date emphistFechaRetiro;

    /**
     * Identificador del cargo al momento del retiro.
     */
    private Integer emphistCargoId;

    /**
     * Identificador del departamento al momento del retiro.
     */
    private Integer emphistDptoId;

    /**
     * Obtiene el identificador único del histórico.
     *
     * @return El identificador único del histórico.
     */
    public Integer getEmphistId() {
        return emphistId;
    }

    /**
     * Establece el identificador único del histórico.
     *
     * @param emphistId El nuevo identificador único del histórico.
     */
    public void setEmphistId(Integer emphistId) {
        this.emphistId = emphistId;
    }

    /**
     * Obtiene la fecha de retiro del empleado.
     *
     * @return La fecha de retiro del empleado.
     */
    public Date getEmphistFechaRetiro() {
        return emphistFechaRetiro;
    }

    /**
     * Establece la fecha de retiro del empleado.
     *
     * @param emphistFechaRetiro La nueva fecha de retiro del empleado.
     */
    public void setEmphistFechaRetiro(Date emphistFechaRetiro) {
        this.emphistFechaRetiro = emphistFechaRetiro;
    }

    /**
     * Obtiene el identificador del cargo al momento del retiro.
     *
     * @return El identificador del cargo al momento del retiro.
     */
    public Integer getEmphistCargoId() {
        return emphistCargoId;
    }

    /**
     * Establece el identificador del cargo al momento del retiro.
     *
     * @param emphistCargoId El nuevo identificador del cargo al momento del retiro.
     */
    public void setEmphistCargoId(Integer emphistCargoId) {
        this.emphistCargoId = emphistCargoId;
    }

    /**
     * Obtiene el identificador del departamento al momento del retiro.
     *
     * @return El identificador del departamento al momento del retiro.
     */
    public Integer getEmphistDptoId() {
        return emphistDptoId;
    }

    /**
     * Establece el identificador del departamento al momento del retiro.
     *
     * @param emphistDptoId El nuevo identificador del departamento al momento del retiro.
     */
    public void setEmphistDptoId(Integer emphistDptoId) {
        this.emphistDptoId = emphistDptoId;
    }
}

