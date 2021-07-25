package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Productor {
    private String nombres;
    private String apellidos;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id_Productor;

    // lotes que posee el productor
    @OneToMany
    private List<Lote> lotes = new ArrayList<>();

    // cuadros que posee el productor
    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();

    public Productor(){}

    public Productor(String nombres, String apellidos){
        this.nombres = nombres;                  
        this.apellidos = apellidos; 
    }

    public Productor(String nombres, String apellidos, List<Lote> lotes, List<Cuadro> cuadros){
        this.nombres = nombres;                  
        this.apellidos = apellidos; 
        this.lotes = lotes ;
        this.cuadros = cuadros; 
    }

    @Override
    public String toString() {
        return "Productor con id_Productor " + id_Productor + "\nNombres: " + nombres 
        + "\nApellido: " + apellidos  + "\n ";
    }





    // getters & adders & setters
       
    public String getId_productor() {
        return id_Productor;
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


}
