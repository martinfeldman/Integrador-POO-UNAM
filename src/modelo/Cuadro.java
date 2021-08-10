package modelo;

import jakarta.persistence.*;

@Entity
public class Cuadro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCuadro;
    private Double superficie;
    private boolean alta;

    @ManyToOne
    private Lote lote;

    

    public Cuadro(){}

    public Cuadro(Lote lote, Double superficie){
        this.lote = lote;
        this.superficie = superficie; 
        lote.agregarCuadro(this);
    }
    
    @Override
    public String toString() {
        return "Cuadro " + Integer.toString(idCuadro);
    }



    

    // getters & setters 
  
    
    public int getIdCuadro() {
        return idCuadro;
    }


    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }


    public Double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }
    

    public boolean isAlta() {
        return alta; 
    }

    public void setAlta(boolean alta) {
       this.alta = alta;
    }
}
