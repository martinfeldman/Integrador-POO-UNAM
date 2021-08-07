package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class EntregaSecadero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_Entrega;

    private Double pesoSecadero;
    private Double pesoCampo;
    private LocalDate fechaEntrega;
    private boolean isAlta;
    
    // las cosechas que llegaron en esta entrega
    @OneToMany
    private List<Cosecha> cosechas = new ArrayList<>();


    public EntregaSecadero() {}

    public EntregaSecadero(List<Cosecha> cosechas, Double pesoSecadero, LocalDate fechaEntrega){
        this.cosechas = cosechas; 
        this.pesoCampo = this.obtenerPesoCampo(cosechas);
        this.pesoSecadero = pesoSecadero;
        this.fechaEntrega = fechaEntrega;
    }
   
    // Este metodo debe sumar las cantidades totales de los Arrays KgsEntregados 
    // de cada cosecha que ingresa como parte de la Entrega - ( en cosechas[] )
    public Double obtenerPesoCampo(List<Cosecha> cosechas) {
        double pesoCampo = 0;
        
        for(Cosecha obj_cos : cosechas){
            //System.out.println(obj_cos);
            pesoCampo = obj_cos.getTotalKgs();
        }  

        return pesoCampo;
        
    }

    @Override
    public String toString() {
        return "EntregaCosechaSecadero [fechaEntrega=" + fechaEntrega + ", idEntrega=" + id_Entrega + ", pesoCampo="
                + pesoCampo + ", pesoSecadero=" + pesoSecadero + "]";
    }





    // getters & setters 


    public int getId_Entrega() {
        return id_Entrega;
    }    

        
    public boolean isAlta() {
        return isAlta;
    }

    public void setAlta(boolean isAlta) {
        this.isAlta = isAlta;
    }

    public Double getPesoCampo() {
        return pesoCampo;
    }

    public void setPesoCampo(Double pesoCampo) {
        this.pesoCampo = pesoCampo;
    }

    public Double getPesoSecadero() {
        return pesoSecadero;
    }

    public void setPesoSecadero(Double pesoSecadero) {
        this.pesoSecadero = pesoSecadero;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
  
    
     
}
 



 



