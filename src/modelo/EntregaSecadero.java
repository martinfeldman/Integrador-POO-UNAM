package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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
    private Date fechaEntrega;
    private boolean isAlta;
    
    // las cosechas que llegaron en esta entrega
    @OneToMany
    private List<Cosecha> cosechas = new ArrayList<>();


    public EntregaSecadero() {}

    public EntregaSecadero(List<Cosecha> cosechas, Double pSecadero, Date fEntrega){
        this.cosechas = cosechas; 
        this.pesoCampo = this.obtenerPesoCampo(cosechas);
        this.pesoSecadero = pSecadero;
        this.fechaEntrega = fEntrega;
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

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
  
    
     
}
 



 



