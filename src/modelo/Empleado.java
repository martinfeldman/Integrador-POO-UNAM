package modelo;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name= "empleados")
public class Empleado {
    public static final String empleado = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idEmpleado")
    private int idEmpleado;

    @Column(name = "nombres_empleado", nullable = false, length = 50)
    private String nombres;

    @Column(name = "apellidos_empleado", nullable = false, length = 50)
    private String apellidos;

    @Column(name = "dni_empleado", nullable = false, length = 50)
    private String dni; 

    @JoinColumn(name = "cosechas_empleado")
    @OneToMany (mappedBy = "empleado")
    private List<Cosecha> cosechas = new ArrayList<>();

    @Column(name = "alta", nullable = false)
    private boolean alta;
   

    public Empleado(){}

    public Empleado(String nombres, String apellidos, String dni){
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.alta = true;
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


    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }



}
