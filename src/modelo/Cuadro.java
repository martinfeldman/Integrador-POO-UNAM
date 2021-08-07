package modelo;

import jakarta.persistence.*;

@Entity
public class Cuadro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Cuadro;
    private double superficie;
    private boolean isAlta;

   // @ManyToOne
    private Productor productor;

    @ManyToOne
    private Lote lote;

    

    public Cuadro(){}

    public Cuadro(Productor productor, Lote lote, double superficie){
        this.productor = lote.getProductor();
        this.lote = lote;
        this.superficie = superficie; 
    }
    
    @Override
    public String toString() {
        return "Cuadro [id_Cuadro=" + id_Cuadro +  ", productor=" + productor + ", superficie="
                + superficie + "]";
    }



    

    // getters & setters 
  
    
    public int getId_Cuadro() {
        return id_Cuadro;
    }

    public boolean isAlta() {
        return isAlta;
    }


    public void setAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }

    public Productor getProductor() {
        return productor;
    }

    public void setProductor(Productor productor) {
        this.productor = productor;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

  
    

   
    

}
