package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCosecha;
    private LocalDate fecha;
    private Cuadro cuadro; 
    private Double kgsCosechados; 
    private boolean isAlta; 
      
    // empleado que produjo la cosecha
    @ManyToOne
    private Empleado empleado;



   

    
    public Cosecha() {}
    
    public Cosecha(Empleado empleado, LocalDate fecha, Cuadro cuadro, Double kgCosechados) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.cuadro = cuadro;
        this.kgsCosechados = kgCosechados;
    }
    
    @Override
    public String toString() {
        return "Cosecha " + Integer.toString(idCosecha);
    }





    // getters & setters 

    public int getIdCosecha() {
        return idCosecha;
    }

    

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isAlta() {
        return isAlta;
    }

    public void setAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }


    public Double getKgsCosechados() {
        return this.kgsCosechados; 
    }

    public void setKgCosechados(Double kgsCosechados) {
        this.kgsCosechados = kgsCosechados;
    }

    public Cuadro getCuadro() {
        return cuadro;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
   

 

 

  

  

 

