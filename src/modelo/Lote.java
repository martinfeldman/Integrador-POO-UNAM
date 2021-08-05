package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Lote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Lote;
    private boolean isAlta;

    @ManyToOne
    private Productor productor;

    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();

    public Lote(){}

    public Lote(Productor productor){
        this.productor = productor;
        productor.agregarLote(this);     
    }
    
    @Override
    public String toString() {
        return "Lote [id_Lote=" + id_Lote + "productor=" + productor + "cuadros=" + cuadros + "]";
    }




    // adders

    public void agregarCuadro(Cuadro cuadro){
        this.cuadros.add(cuadro);
    }
    

    // getters & setters

    public int getId_Lote() {
        return id_Lote;
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

    public List<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }

}