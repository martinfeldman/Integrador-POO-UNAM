package modelo;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EntregaCosechaSecadero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int idEntrega;
    private Double pesoSecadero;
    private Double pesoCampo;
    private Date fechaEntrega;
    
    public EntregaCosechaSecadero(){}

    public EntregaCosechaSecadero(Double pSecadero, Double pCampo, Date fEntrega){
        this.pesoSecadero = pSecadero;
        this.pesoCampo = pCampo;
        this.fechaEntrega = fEntrega;
    }
    
    @Override
    public String toString() {
        return "EntregaCosechaSecadero [fechaEntrega=" + fechaEntrega + ", idEntrega=" + idEntrega + ", pesoCampo="
                + pesoCampo + ", pesoSecadero=" + pesoSecadero + "]";
    }

    // getters & setters 

    public Double getPesoSecadero() {
        return pesoSecadero;
    }

    public void setPesoSecadero(Double pesoSecadero) {
        this.pesoSecadero = pesoSecadero;
    }

    public Double getPesoCampo() {
        return pesoCampo;
    }

    public void setPesoCampo(Double pesoCampo) {
        this.pesoCampo = pesoCampo;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    
    
    
    
    
     
}
