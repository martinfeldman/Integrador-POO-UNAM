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
    protected String idEmpleado;
    @ManyToMany
    private List<Productor> productores = new ArrayList<>();

 
    public Empleado(){}

    public Empleado(String nombres){
        this.nombres = nombres;
    }
    
    @Override
    public String toString() {
        return "Empleado con idEmpleado = " + idEmpleado + ", nombre=" + nombres + ", productores="
                + productores + "]";
    }







    // getters & adders & setters

    public String getNombre() {
        return nombres;
    }

    public void setNombre(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public List<Productor> getProductores() {
        return productores;
    }

    public void agregarProductor(Productor productor) {
        this.productores.add(productor);
    }


    
    

}
