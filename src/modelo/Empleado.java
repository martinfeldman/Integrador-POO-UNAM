package modelo;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
public class Empleado {
    private String nombres;
    private String apellidos;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String id_Empleado;

    @OneToMany
    private List<Cosecha> cosechas = new ArrayList<>();

 
    public Empleado(){}

    public Empleado(String nombres){
        this.nombres = nombres;
    }
    
    @Override
    public String toString() {
        return "Empleado con id_Empleado " + id_Empleado + "\nNombres: " + nombres 
        + "\nApellido: " + apellidos  + "\n ";
    }







    // getters & adders & setters

    public String getNombre() {
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
      
    public String getId_Empleado() {
        return id_Empleado;
    }
    

}
