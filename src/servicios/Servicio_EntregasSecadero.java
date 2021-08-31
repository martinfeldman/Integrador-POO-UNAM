package servicios;
import java.time.LocalDate;
import java.util.List;

import modelo.Cosecha;
import modelo.EntregaSecadero;
import repositorios.Repositorio;

public class Servicio_EntregasSecadero {
   
    private Repositorio repositorio;

    public Servicio_EntregasSecadero(Repositorio p) {
        this.repositorio = p;
    }


    // Listar y Buscar  
    public List<EntregaSecadero> listarEntregaSecadero() {
        return this.repositorio.buscarTodos(EntregaSecadero.class);
        // cambiar por buscar todos ordenados por
    }

    public EntregaSecadero buscarEntregaSecadero(int id_EntregaSecadero) {
        return this.repositorio.buscar(EntregaSecadero.class, id_EntregaSecadero);
    }

 



   // ABM ENTREGA a SECADERO 

  
   public void agregarEntregaSecadero(Cosecha cosecha ,LocalDate fechaEntrega, Double pesoSecadero ) {

        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL
        if (cosecha == null || fechaEntrega == null ||pesoSecadero.toString().trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        EntregaSecadero entregaSecadero = new EntregaSecadero(cosecha, fechaEntrega, pesoSecadero);

        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(entregaSecadero);
        this.repositorio.confirmarTransaccion();
    }



    
    public boolean modificarEntregaSecadero(int idEntregaSecadero, Cosecha cosechaNueva, Double pesoSecadero, LocalDate fechaEntrega) {
        
        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL 
        if (cosechaNueva == null || pesoSecadero.toString() == null ||  fechaEntrega == null) {
            throw new IllegalArgumentException("Faltan datos");
        }

        //- buscar la EntregaSecadero en la base de datos a partir de su ID
        EntregaSecadero entregaSecadero = this.repositorio.buscar(EntregaSecadero.class, idEntregaSecadero);
        
        //- si regresa un objeto EntregaSecadero, se hacen TODAS las modificaciones debidas,
        //- incluyendo las dependencias en otras clases y se inicia una transaccion 
        if (entregaSecadero != null) {

             //- modificaciones al objeto

             var cosechaParaQuitar = entregaSecadero.getCosecha();
             this.repositorio.iniciarTransaccion();    
             
             //- Solo se cambia la cosecha de la EntregaSecadero si es diferente de la que ya posee    
             if (! cosechaParaQuitar.equals(cosechaNueva)) {
         
                 entregaSecadero.setCosecha(cosechaNueva);     
            
             } else {
                 System.out.print("cosechaParaQuitar es igual a cosechaNueva. Se omite esta modificación.\n");
             }

            entregaSecadero.setPesoSecadero(pesoSecadero); 
            entregaSecadero.setFechaEntrega(fechaEntrega);
            entregaSecadero.setDiferenciaPeso(pesoSecadero - cosechaNueva.getKgsCosechados() );

       
            //- persistir en la bd todo objeto que haya sido modificado    
            this.repositorio.modificar(entregaSecadero);
            this.repositorio.confirmarTransaccion();
            return true; 

        //- sino se informa y se retorna modificarObjeto = falso
        } else {
            System.out.print("repositorio.buscar(idLote) = NULL \n\n");
            return false;
        }
    }



    public boolean eliminarEntregaSecadero(int idEntregaSecadero) {
       // se implementa borrado logico
        
        // buscar el productor en la base de datos a partir de su ID 
        EntregaSecadero entregaSecadero = this.repositorio.buscar(EntregaSecadero.class, (Object) idEntregaSecadero);

        // si bd no retorna objeto es porque no existe, eliminarProductor devuelve falso
        if (entregaSecadero == null) {
            System.out.print("repositorio.buscar(idProductor) = NULL \n\n");
            return false;

        // sino comienza una transaccion con bd 
        // se da de baja el productor, sus lotes y cuadros y se confirma transaccion
        } else {
            this.repositorio.iniciarTransaccion();

            // dar de baja cosecha asociada a la entregaSecadero
            
            entregaSecadero.getCosecha().setAlta(false);
            this.repositorio.modificar(entregaSecadero.getCosecha()); 

            entregaSecadero.setAlta(false);
            this.repositorio.modificar(entregaSecadero);

            this.repositorio.confirmarTransaccion();
            
            return true;
        }
    }


}
         
 