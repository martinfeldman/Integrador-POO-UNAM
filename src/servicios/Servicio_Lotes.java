package servicios;
import java.util.List;

import modelo.Empleado;
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
    public void agregarLote(Productor productor) {
        if (productor == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Lote lote = new Lote(productor);
        this.repositorio.insertar(lote);
        this.repositorio.confirmarTransaccion();
    }


    
    public boolean editarLote(int idLote, Productor productor) {
        if (productor == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        
        Lote lote = this.repositorio.buscar(Lote.class, idLote);
        if (lote != null) {
            lote.setProductor(productor);
            //lote.setNombres(nombres);

            // implementar comparable o comparator
            // o si el id es unico pueden compararar por id
            /* if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            }  */
            this.repositorio.modificar(lote);
            this.repositorio.confirmarTransaccion();
            return true;
        } else {
            this.repositorio.descartarTransaccion();
            return false;
        }
    }



    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public boolean eliminarLote(int idLote) {
        this.repositorio.iniciarTransaccion();
        Lote lote = this.repositorio.buscar(Lote.class, idLote);
        // como se soluciona??
        /* if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(empleado);
            this.repositorio.confirmarTransaccion();
            return true;
        } else { */ 
            this.repositorio.descartarTransaccion();
            return false;
        //}
    }

}
