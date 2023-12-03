package co.poli.rhserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "historico")
public class Historico {

    @Id
    private Integer emphistId;
    private Date emphistFechaRetiro;
    private Integer emphistCargoId;
    private Integer emphistDptoId;

    public Integer getEmphistId() {
        return emphistId;
    }

    public void setEmphistId(Integer emphistId) {
        this.emphistId = emphistId;
    }

    public Date getEmphistFechaRetiro() {
        return emphistFechaRetiro;
    }

    public void setEmphistFechaRetiro(Date emphistFechaRetiro) {
        this.emphistFechaRetiro = emphistFechaRetiro;
    }

    public Integer getEmphistCargoId() {
        return emphistCargoId;
    }

    public void setEmphistCargoId(Integer emphistCargoId) {
        this.emphistCargoId = emphistCargoId;
    }

    public Integer getEmphistDptoId() {
        return emphistDptoId;
    }

    public void setEmphistDptoId(Integer emphistDptoId) {
        this.emphistDptoId = emphistDptoId;
    }
}
