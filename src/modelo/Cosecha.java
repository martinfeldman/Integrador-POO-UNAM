package modelo;

import jakarta.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id_Cosecha;
    private int id_Lote;
    private ArrayList<Double> kgEntregados = new ArrayList<>();
  

    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();
    private Date fecha;
    @OneToOne
    private Cuadrilla cuadrilla;
    
    protected Cosecha() {}
    
    public Cosecha(int idLote, Date fecha, Cuadrilla cuadrilla) {
        this.id_Lote = idLote;
        this.fecha = fecha;
        this.cuadrilla = cuadrilla;
    }
    
    @Override
    public String toString() {
        return "Cosecha [cuadrilla=" + cuadrilla + ", cuadros=" + cuadros + ", fecha=" + fecha + ", idCosecha="
                + id_Cosecha + ", idLote=" + id_Lote + ", kgEntregados=" + kgEntregados + "]";
    }


    public void agregarCuadro(Cuadro cuadro) {
        this.cuadros.add(cuadro);
    }

    public void agregarKgEntregados(Double kgEntregados) {
        this.kgEntregados.add(kgEntregados);
    }


    


    // getters & setters 

    public int getIdLote() {
        return id_Lote;
    }

    public void setIdLote(int idLote) {
        this.id_Lote = idLote;
    }

    public int getId_Cosecha() {
        return id_Cosecha;
    }

    public void setIdCosecha(int idCosecha) {
        this.id_Cosecha = idCosecha;
    }

    public ArrayList<Double> getKgEntregados() {
        return kgEntregados;
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

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
    }
}
   

 

 

  

  

 

