package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Cosecha;
    private LocalDate fecha;
    private boolean isAlta; 
      
    // empleado que produjo la cosecha
    @ManyToOne
    private Empleado empleado;

    // los cuadros cosechados y los kilos según el cuadro. Se utiliza correspondencia entre arrays.
    // Es decir kgsEntregados[0] = kgsEntregados por cuadro[0],
    //          kgsEntregados[1] = kgsEntregados por cuadro[1], etc.
    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();
    private ArrayList<Double> kgsCosechados = new ArrayList<>();

    
    public Cosecha() {}
    
    public Cosecha(Empleado empleado, LocalDate fecha, List<Cuadro> cuadros, ArrayList<Double> kgEntregados ) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.cuadros = cuadros;
        this.kgsCosechados = kgEntregados;
    }
    
    @Override
    public String toString() {
        return "Cosecha [realizada por empleado: " + empleado.getNombres() + ", cuadros=" + cuadros + ", fecha=" + fecha + ", idCosecha="
                + id_Cosecha +  ", kgEntregados=" + kgsCosechados + "]";
    }





    // adders 
    
    public void agregarCuadro(Cuadro cuadro) {
        this.cuadros.add(cuadro);
    }

    public void agregarKgEntregados(Double kgEntregados) {
        this.kgsCosechados.add(kgEntregados);
    }


    


    // getters & setters 

    public int getId_Cosecha() {
        return id_Cosecha;
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

    public Double getTotalKgs() {
        double pesoTotal = 0;

        for(Double index : this.kgsCosechados){
            //System.out.println(index);
            pesoTotal += index;        
        }

        return pesoTotal;
    }

    public ArrayList<Double> getkgsCosechados() {
        return this.kgsCosechados; 
    }

    public void setKgEntregados(ArrayList<Double> kgEntregados) {
        this.kgsCosechados = kgEntregados;
    }

    public List<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
   

 

 

  

  

 

