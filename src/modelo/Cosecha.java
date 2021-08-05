package modelo;

import jakarta.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Cosecha;
    private Date fecha;
    private boolean isAlta; 
      
    // empleado que produjo la cosecha
    @ManyToOne
    private Empleado empleado;

    // los cuadros cosechados y los kilos según el cuadro. Se utiliza correspondencia entre arrays.
    // Es decir kgsEntregados[0] = kgsEntregados por cuadro[0],
    //          kgsEntregados[1] = kgsEntregados por cuadro[1], etc.
    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();
    private ArrayList<Double> kgEntregados = new ArrayList<>();

    
    public Cosecha() {}
    
    public Cosecha(Empleado empleado, Date fecha, List<Cuadro> cuadros, ArrayList<Double> kgEntregados ) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.cuadros = cuadros;
        this.kgEntregados = kgEntregados;
    }
    
    @Override
    public String toString() {
        return "Cosecha [realizada por empleado: " + empleado.getNombres() + ", cuadros=" + cuadros + ", fecha=" + fecha + ", idCosecha="
                + id_Cosecha +  ", kgEntregados=" + kgEntregados + "]";
    }





    // adders 
    
    public void agregarCuadro(Cuadro cuadro) {
        this.cuadros.add(cuadro);
    }

    public void agregarKgEntregados(Double kgEntregados) {
        this.kgEntregados.add(kgEntregados);
    }


    


    // getters & setters 

    public int getId_Cosecha() {
        return id_Cosecha;
    }

    public boolean isAlta() {
        return isAlta;
    }

    public void setAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }

    public Double getTotalKgs() {
        double pesoTotal = 0;

        for(Double index : this.kgEntregados){
            //System.out.println(index);
            pesoTotal += index;        
        }

        return pesoTotal;
    }

    public ArrayList<Double> getKgEntregados() {
        return this.kgEntregados; 
    }

    public void setKgEntregados(ArrayList<Double> kgEntregados) {
        this.kgEntregados = kgEntregados;
    }

    public List<Cuadro> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<Cuadro> cuadros) {
        this.cuadros = cuadros;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
   

 

 

  

  

 

