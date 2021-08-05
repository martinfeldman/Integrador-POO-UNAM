package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Productor {
    private String nombres;
    private String apellidos;
    private String dni;
    private boolean isAlta;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Productor;

    // lotes que posee el productor
    @OneToMany
    private List<Lote> lotes = new ArrayList<>();

    // cuadros que posee el productor
    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();

    public Productor(){}

    public Productor(String nombres, String apellidos, String dni){
        this.nombres = nombres;                  
        this.apellidos = apellidos; 
        this.dni = dni ;
    }

    @Override
    public String toString() {
        return "Productor con id_Productor " + id_Productor + "\nNombres: " + nombres 
        + "\nApellido: " + apellidos  + "\n ";
    }




    // adders

    public void agregarLote(Lote lote){
        this.lotes.add(lote); 
    }



    // getters &  setters
       
    public int getId_productor() {
        return id_Productor;
    }
    
    public boolean isAlta() {
        return isAlta;
    }

    public void setAlta(boolean isAlta) {
        this.isAlta = isAlta;
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

    public List<Cuadro> getCuadros() {
        return cuadros;
    }


    /* 

    public void setCuadros(List<Cuadro> cuadros) {
        this.cuadros = cuadros;
    } 
    
     public void setLotes(List<Lote> lotes) {
        this.lotes = lotes;
    }   
    
    */
    
}
