package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name= "productores")
public class Productor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProductor")
    private int idProductor;

    @Column(name = "nombres_productor", nullable = false, length = 50)
    private String nombres;

    @Column(name = "apellidos_productor", nullable = false, length = 50)
    private String apellidos;

    @Column(name = "dni_productor", nullable = false, length = 15)
    private String dni;
    
    // lotes que posee el productor
    @JoinColumn(name = "lotes_productor")
    @OneToMany (mappedBy = "productor")
    private List<Lote> lotes = new ArrayList<>();

    @Column(name = "alta_productor", nullable = false)
    private boolean alta;


    public Productor(){}

    public Productor(String nombres, String apellidos, String dni){
        this.nombres = nombres;                  
        this.apellidos = apellidos; 
        this.dni = dni ;
        this.alta = true;
    }



    @Override
    public String toString() {
        return nombres + " " + apellidos + ", (" + dni + ")\n";
    }



    // add & remove

    public void agregarLote(Lote lote){
        this.lotes.add(lote); 
    }

    public void quitarLote(Lote lote){
        this.lotes.remove(lote);
    }



    // getters &  setters
       
    public int getIdProductor() {
        return idProductor;
    }
    

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }


    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public List<Lote> getLotes() {
        return lotes;
    }


    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }
    
}
