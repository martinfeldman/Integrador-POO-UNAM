package servicios;
import java.util.List;

import modelo.Lote;
import modelo.Productor;
import repositorios.Repositorio;


public class Servicio_Productores {

    private Repositorio repositorio;

    public Servicio_Productores(Repositorio p) {
        this.repositorio = p;
    }

    // Listar y Buscar Productor

    public List<Productor> listarProductores() {
        return this.repositorio.buscarTodos(Productor.class);
        // cambiar por buscar todos ordenados por
    }

    public Productor buscarProductor(int idProductor) {
        return this.repositorio.buscar(Productor.class, idProductor);
    }





    // ABM PRODUCTOR 

    public void agregarProductor(String nombres, String apellidos, String dni) {

        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 ||
         dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        Productor productor = new Productor(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), dni.trim());
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(productor);
        this.repositorio.confirmarTransaccion();
    }


   
    public boolean modificarProductor(int idProductor, String nombres, String apellidos, String dni) {

        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL 
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        //- buscar al productor en la base de datos a partir de su ID 
        Productor productor = this.repositorio.buscar(Productor.class, idProductor);

        //- si regresa un objeto productor, se hacen TODAS las modificaciones debidas,
        //- incluyendo las dependencias en otras clases y se inicia una transaccion 
        if (productor != null) {

        //- modificaciones al objeto
            productor.setNombres(nombres);
            productor.setApellidos(apellidos);
            productor.setDni(dni);

            this.repositorio.iniciarTransaccion();
            this.repositorio.modificar(productor);
            this.repositorio.confirmarTransaccion();
            return true;

        //- sino se informa y se retorna modificarObjeto = falso 
        } else {
            System.out.print("repositorio.buscar(idProductor) = NULL");
            return false;
        }
    }

    
    
    public boolean eliminarProductor(int idProductor) {
        // se implementa borrado logico
        
        // buscar el productor en la base de datos a partir de su ID 
        Productor productor = this.repositorio.buscar(Productor.class, (Object) idProductor);

        // si bd no retorna objeto es porque no existe, y se cancela transaccion
        if (productor == null) {
            this.repositorio.descartarTransaccion();
            return false;

        // sino comienza una transaccion con bd 
        // se da de baja el productor, sus cuadros y lotes y se confirma transaccion
        } else {
            this.repositorio.iniciarTransaccion();

            // dar de baja lotes 
            for(Lote lote : productor.getLotes()){
                lote.setAlta(false);                       
            }

            productor.setAlta(false);

            this.repositorio.confirmarTransaccion();
            
            return true;
    
            // this.repositorio.eliminar(productor);     
           
        }
    }

   
}
