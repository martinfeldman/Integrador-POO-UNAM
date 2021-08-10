package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Lote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idLote;
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

        // como deberia ser el toString de Cuadros : {cuadro1, cuadro2, etc  }
        return "Lote " + idLote;
    }




    // add  & remove

    public void agregarCuadro(Cuadro cuadro){
        this.cuadros.add(cuadro);
    }
    
    public void quitarCuadro(Cuadro cuadro){
        this.cuadros.remove(cuadro);
    }


    
    // getters & setters

    public int getIdLote() {
        return idLote;
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