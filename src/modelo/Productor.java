package modelo;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Productor {
    
    //Atributos de la Clase-Entidad
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long idProductor;
    @ManyToMany
    private List<Empleado> empleado = new ArrayList<>();
    private String nombre;
  
    public Productor(){}

    public Productor(String nombre){
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "VACIO";
    }

    // getters & adders & setters
    
    public List<Empleado> getEmpleado() {
        return empleado;
    }
    public void agregarEmpleado(Empleado empleado) {
        this.empleado.add(empleado);
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

   
    



}
