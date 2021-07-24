package modelo;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
public class Lote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int idLote;
    private Date fechaInicio;
    private Date fechaFin;

    public Lote(){}

    public Lote(Date fInicio, Date fFin){
        this.fechaInicio = fInicio;
        this.fechaFin = fFin;
    }
    
    @Override
    public String toString() {
        return "Lote [fechaFin=" + fechaFin + ", fechaInicio=" + fechaInicio + ", idLote=" + idLote + "]";
    }





    // getters & setters
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
        
}