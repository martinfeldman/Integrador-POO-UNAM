package servicios;
import java.util.List;

import modelo.Cuadro;
import modelo.Lote;
import modelo.Productor;
import repositorios.Repositorio;

    // para este Servicio se utilizó como referencia Servicio_empleado del ejemploIntegrador

public class Servicio_Lotes{

    private Repositorio repositorio;


    public Servicio_Lotes(Repositorio p) {
        this.repositorio = p;
    }
    


     // Listar y Buscar

    public List<Lote> listarLotes() {
        return this.repositorio.buscarTodos(Lote.class);
        // cambiar por buscar todos ordenados por
    }

    public Lote buscarLote(int idLote) {
        return this.repositorio.buscar(Lote.class, idLote);
    }
    


    // ABM LOTE 

    // para agregar un lote solo se requiere el productor 
    public boolean agregarLote(Productor productor) {
        if (productor == null) {
            throw new IllegalArgumentException("Faltan datos");
        }

        Lote lote = new Lote(productor);

        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(lote);
        this.repositorio.confirmarTransaccion();

        //- agregamos el lote a productor.lotes[] 
        productor.agregarLote(lote);
        return true;
    }


    
    public boolean modificarLote(int idLote, Productor productorNuevo) {
       
        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL 
        if (productorNuevo == null) {
            throw new IllegalArgumentException("Faltan datos");
        }

        //- buscar el lote en la base de datos a partir de su ID
        Lote lote = this.repositorio.buscar(Lote.class, idLote);

        //- si regresa un objeto productor, se hacen TODAS las modificaciones debidas,
        //- incluyendo las dependencias en otras clases y se inicia una transaccion 
        if (lote != null) {
            
        //- dependencias de la modificacion    
            
            var productorParaQuitar = lote.getProductor();

        //- Solo se cambia el productor del lote si es diferente del que ya posee    
            if (! productorParaQuitar.equals(productorNuevo)) {
                
                this.repositorio.iniciarTransaccion();

                productorParaQuitar.quitarLote(lote); 
                productorNuevo.agregarLote(lote);
            
            } else {
                System.out.print("ProductorParaQuitar es igual a ProductorNuevo. Se omite esta modificación.\n");
                return false;
            }

            
        //- modificaciones al objeto
            lote.setProductor(productorNuevo);
            
            
        //- persistir en la bd todo objeto que haya sido modificado  

            this.repositorio.modificar(productorParaQuitar);
            this.repositorio.modificar(productorNuevo);
            this.repositorio.modificar(lote);
            this.repositorio.confirmarTransaccion();
            return true;

        //- sino se informa y se retorna modificarObjeto = falso    
        } else {
            System.out.print("repositorio.buscar(idLote) = NULL \n\n");
            return false;
        }
    }




    public boolean eliminarLote(int idLote) {

        // se implementa borrado logico
        
        // buscar el lote en la base de datos a partir de su ID 
        Lote lote = this.repositorio.buscar(Lote.class, (Object) idLote);

        // si bd no retorna objeto es porque no existe, eliminarProductor devuelve falso
        if (lote == null) {
            System.out.print("repositorio.buscar(idProductor) = NULL \n\n");
            return false;

        // sino comienza una transaccion con bd 
        // se da de baja el productor, sus lotes y cuadros y se confirma transaccion
        } else {
            this.repositorio.iniciarTransaccion();

            // dar de baja cuadros de Lote 
            for (Cuadro cuadro : lote.getCuadros()){
                cuadro.setAlta(false);
                this.repositorio.modificar(cuadro);      
            }

            // quitar el lote de la lista de lotes del productor
            lote.getProductor().quitarLote(lote);
            this.repositorio.modificar(lote.getProductor()); 

            lote.setAlta(false);
            this.repositorio.modificar(lote);
            
            this.repositorio.confirmarTransaccion();
            
            return true;
        }
    }

}
