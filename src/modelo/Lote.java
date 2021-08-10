package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name= "lotes")
public class Lote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idLote")
    private int idLote;

    @JoinColumn(name = "productor_lote", nullable = false)
    private Productor productor;

    @JoinColumn(name = "cuadro_lote", nullable = false)
    @OneToMany (mappedBy = "lote")
    private List<Cuadro> cuadros = new ArrayList<>();

    @Column(name = "alta", nullable = false)
    private boolean alta;


    public Lote(){}

    public Lote(Productor productor){
        this.productor = productor;
        productor.agregarLote(this);  
        this.alta = true;   
    }
    


    @Override
    public String toString() {
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


    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

}