package modelo;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Empleado;

    @OneToMany
    private List<Cosecha> cosechas = new ArrayList<>();

    private String nombres;
    private String apellidos;
    private String dni; 
    private boolean isAlta;
   

    public Empleado(){}

    public Empleado(String nombres, String apellidos, String dni){
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
    }
    
    @Override
    public String toString() {
        return "Empleado con id_Empleado " + id_Empleado + "\nNombres: " + nombres 
        + "\nApellido: " + apellidos  + "\n ";
    }





    // adders

    // agregar 


    // getters &  setters

    public int getId_Empleado() {
        return id_Empleado;
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

    public List<Cosecha> getCosechas() {
        return cosechas;
    }


}
