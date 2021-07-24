package modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cuadrilla {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int idCuadrilla;
    @OneToMany
    private List<Empleado> empleados = new ArrayList<>();
    
    protected Cuadrilla(){}
    public Cuadrilla(Empleado empleado){
        this.empleados.add(empleado);
    }
    
    @Override
    public String toString() {
        return "Cuadrilla [empleados=" + empleados + ", idCuadrilla=" + idCuadrilla + "]";
    }




    // getter & adders
      
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void agregarEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }
   
   
    

    
}
