package modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name= "entregas_secadero")
public class EntregaSecadero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idEntrega")
    private int idEntrega;

    @JoinColumn(name = "cosecha_entrega", nullable=false)
    private Cosecha cosecha;

    @Column(name = "pesoSecadero", nullable=false, length = 50)
    private Double pesoSecadero;

    @Column(name = "pesoCampo", nullable=false, length = 50)
    private Double pesoCampo;

    @Column(name = "diferenciaPeso", nullable=false, length = 50)
    private Double diferenciaPeso;

    @Column(name = "fechaEntrega", nullable=false)
    private LocalDate fechaEntrega;

    @Column(name = "alta", nullable=false)
    private boolean alta;


    public EntregaSecadero() {}

    public EntregaSecadero(Cosecha cosecha, LocalDate fechaEntrega, Double pesoSecadero){
        this.cosecha = cosecha; 
        this.fechaEntrega = fechaEntrega;
        this.pesoSecadero = pesoSecadero;
        this.pesoCampo = cosecha.getKgsCosechados();
        this.diferenciaPeso = pesoSecadero - cosecha.getKgsCosechados();
        this.alta = true;
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
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }
}

