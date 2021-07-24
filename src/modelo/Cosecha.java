package modelo;

import jakarta.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int idCosecha;
    private int idLote;
    private ArrayList<Double> kgEntregados = new ArrayList<>();
    @OneToMany
    private List<Cuadro> cuadros = new ArrayList<>();
    private Date fecha;
    @OneToOne
    private Cuadrilla cuadrilla;
    
    protected Cosecha() {}
    
    public Cosecha(int idLote, Date fecha, Cuadrilla cuadrilla) {
        this.idLote = idLote;
        this.fecha = fecha;
        this.cuadrilla = cuadrilla;
    }
    
    @Override
    public String toString() {
        return "Cosecha [cuadrilla=" + cuadrilla + ", cuadros=" + cuadros + ", fecha=" + fecha + ", idCosecha="
                + idCosecha + ", idLote=" + idLote + ", kgEntregados=" + kgEntregados + "]";
    }


    public void agregarCuadro(Cuadro cuadro) {
        this.cuadros.add(cuadro);
    }

    public void agregarKgEntregados(Double kgEntregados) {
        this.kgEntregados.add(kgEntregados);
    }


    


    // getters & setters 

    public int getIdLote() {
        return idLote;
    }

    public ArrayList<Double> getKgEntregados() {
        return kgEntregados;
    }

    public List<Cuadro> getCuadros() {
        return cuadros;
    }

    public Date getFecha() {
        return fecha;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    
}
