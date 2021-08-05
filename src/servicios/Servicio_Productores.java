package servicios;
import java.util.List;

import modelo.Cuadro;
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
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 ||
        dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Productor productor = new Productor(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), dni);
        this.repositorio.insertar(productor);
        this.repositorio.confirmarTransaccion();
    }




   
    public boolean editarProductor(int id_Productor, String nombres, String apellidos, String dni) {

        // Execpion si alguno de los datos que toma de la Vista esta vacio,
        // sino comienza una transaccion para editar 
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        this.repositorio.iniciarTransaccion();
    
        // buscar el productor en la base de datos a partir de su ID 
        Productor productor = this.repositorio.buscar(Productor.class, id_Productor);

        // si regresa un objeto productor se cambia sus nombres, apellidos y dni
        if (productor != null) {
            productor.setApellidos(apellidos);
            productor.setNombres(nombres);
            productor.setDni(dni);
            this.repositorio.modificar(productor);
            this.repositorio.confirmarTransaccion();
            return true;

         // sino se descarta transaccion 
        } else {
            this.repositorio.descartarTransaccion();
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

            // dar de baja cuadros
            for(Cuadro cuadro : productor.getCuadros()){
                cuadro.setAlta(false);                       
            }

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
