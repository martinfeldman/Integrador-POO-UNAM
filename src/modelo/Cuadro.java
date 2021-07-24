package modelo;

import jakarta.persistence.*;

@Entity
public class Cuadro {
    //Atributos de la Clase-Entidad
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int idCuadro;
    private int idLote;
    @ManyToOne
    private Productor productor;
    private double superficie;

    protected Cuadro(){}

    public Cuadro(Productor productor, double superficie, int idLote){
        this.productor = productor;
        this.superficie = superficie;
        this.idLote = idLote;   
    }

    public Cuadro(Productor productor){
        this.productor = productor;
    }
    
    @Override
    public String toString() {
        return "Cuadro [idCuadro=" + idCuadro + ", idLote=" + idLote + ", productor=" + productor + ", superficie="
                + superficie + "]";
    }



    

    // getters & setters 
    
    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public Productor getProductor() {
        return productor;
    }

    public void setProductor(Productor productor) {
        this.productor = productor;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

   
    

}
