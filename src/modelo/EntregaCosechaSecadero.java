package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class EntregaCosechaSecadero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id_Entrega;
    private String id_Cosecha;
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
        return "EntregaCosechaSecadero [fechaEntrega=" + fechaEntrega + ", idEntrega=" + id_Entrega + ", pesoCampo="
                + pesoCampo + ", pesoSecadero=" + pesoSecadero + "]";
    }





    // getters & setters 

    public String getId_Entrega() {
        return id_Entrega;
    }

    public void setId_Entrega(String idEntrega) {
        this.id_Entrega = idEntrega;
    }

    public Double getPesoCampo() {
        return pesoCampo;
    }
    
    public void setPesoCampo(Double pesoCampo) {
        this.pesoCampo = pesoCampo;
    }

    public Double getPesoSecadero() {
        return pesoSecadero;
    }

    public void setPesoSecadero(Double pesoSecadero) {
        this.pesoSecadero = pesoSecadero;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
  
    
     
}
 



 



