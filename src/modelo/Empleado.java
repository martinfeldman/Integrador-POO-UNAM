package modelo;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
public class Empleado {
    public static final String empleado = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEmpleado;

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
        return nombres + " " + apellidos + ", (" + dni + ")\n";
    }





    // add & remove

    public void agregarCosecha(Cosecha cosecha){
        this.cosechas.add(cosecha);
    }
    
    public void quitarCosecha(Cosecha cosecha){
        this.cosechas.remove(cosecha);
    }


    // getters &  setters

    public int getIdEmpleado() {
        return idEmpleado;
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
