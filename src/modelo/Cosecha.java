package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "cosechas")
public class Cosecha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCosecha;

    @Column(name = "fecha_cosecha", nullable=false, length = 50)
    private LocalDate fecha;

    // empleado que produjo la cosecha
    @JoinColumn(name = "empleado_cosecha", nullable=false)
    @ManyToOne
    private Empleado empleado;

    @JoinColumn(name = "cuadro_cosechado", nullable=false)
    private Cuadro cuadro; 

    @Column(name = "kgsCosechados", nullable=false, length = 50)
    private Double kgsCosechados; 
      
    @Column(name = "alta", nullable=false)
    private boolean alta; 


    public Cosecha() {}
    
    public Cosecha(Empleado empleado, LocalDate fecha, Cuadro cuadro, Double kgCosechados) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.cuadro = cuadro;
        this.kgsCosechados = kgCosechados;
        this.alta = true;
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


    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

}
   

 

 

  

  

 

