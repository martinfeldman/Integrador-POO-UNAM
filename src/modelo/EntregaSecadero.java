package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class EntregaSecadero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEntrega;

    private Cosecha cosecha;
    private Double pesoSecadero;
    private Double pesoCampo;
    private Double diferenciaPeso;

    private LocalDate fechaEntrega;
    private boolean isAlta;



    public EntregaSecadero() {}

    public EntregaSecadero(Cosecha cosecha, LocalDate fechaEntrega, Double pesoSecadero){
        this.cosecha = cosecha; 
        this.fechaEntrega = fechaEntrega;
        this.pesoSecadero = pesoSecadero;
        this.pesoCampo = cosecha.getKgsCosechados();
        this.diferenciaPeso = cosecha.getKgsCosechados() - pesoSecadero ;
    }


   // @Override
    public String toString() {
        return Integer.toString(idEntrega);
    }





    // getters & setters 


    public int getIdEntrega() {
        return idEntrega;
    }    


    public Cosecha getCosecha() {
        return cosecha;
    }

    public void setCosecha(Cosecha cosecha) {
        this.cosecha = cosecha;
    }


    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    

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


    public Double getDiferenciaPeso() {
        return diferenciaPeso;
    }

    public void setDiferenciaPeso(Double diferenciaPeso) {
        this.diferenciaPeso = diferenciaPeso;
    }

    
    public boolean isAlta() {
        return isAlta;
    }

    public void setAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }
}

