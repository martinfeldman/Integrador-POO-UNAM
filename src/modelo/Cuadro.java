package modelo;

import jakarta.persistence.*;

@Entity
@Table(name= "cuadros")
public class Cuadro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCuadro")
    private int idCuadro;

    @JoinColumn(name = "lote_cuadro", nullable = false)
    @ManyToOne
    private Lote lote;

    @Column(name = "superficieCuadro", nullable = false, length = 50)
    private Double superficie;

    @Column(name = "alta", nullable = false)
    private boolean alta;

    
    public Cuadro(){}

    public Cuadro(Lote lote, Double superficie){
        this.lote = lote;
        this.superficie = superficie; 
        lote.agregarCuadro(this);
        this.alta = true;
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
