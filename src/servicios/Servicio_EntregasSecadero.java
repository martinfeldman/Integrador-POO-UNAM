package servicios;
import repositorios.*;
import modelo.Cosecha;
import modelo.Empleado;
import modelo.EntregaSecadero;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Servicio_EntregasSecadero {
   
    private Repositorio repositorio;

    public Servicio_EntregasSecadero(Repositorio p) {
        this.repositorio = p;
    }
   

   
    // obtener diferencia de kgs entre el peso registrado en el campo y en el secadero 
    public double diffCampoSecadero(){
        return 3.9;
    }


    // Listar y Buscar 
    public List<EntregaSecadero> listarEntregaSecadero() {
        return this.repositorio.buscarTodos(EntregaSecadero.class);
        // cambiar por buscar todos ordenados por
    }

    public EntregaSecadero buscarEntregaSecadero(int id_EntregaSecadero) {
        return this.repositorio.buscar(EntregaSecadero.class, id_EntregaSecadero);
    }

 



   // ABM ENTREGA SECADERO 

   public void agregarEntregaSecadero(ArrayList<Cosecha> cosechas ,double PesoSecadero, Date fechaEntrega) {
        if (cosechas == null) {
        throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        EntregaSecadero entregaSecadero = new EntregaSecadero();
        this.repositorio.insertar(entregaSecadero);
        this.repositorio.confirmarTransaccion();
    }

    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public void editarEntregaSecadero(int idEntregaSecadero, double PesoSecadero, Date fechaEntrega) {
        
        // falta completar el if con los demas atributos
        if (fechaEntrega == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        EntregaSecadero entregaSecadero = this.repositorio.buscar(EntregaSecadero.class, idEntregaSecadero);
        if (entregaSecadero != null) {
            entregaSecadero.setFechaEntrega(fechaEntrega);
            //entregaSecadero.setNombres(nombres);


            // implementar comparable o comparator
            // o si el id es unico pueden compararar por id
           /* if (! entregaSecadero.getDepartamento().equals(departamento)) {
                entregaSecadero.getDepartamento().getEmpleados().remove(empleado);
                entregaSecadero.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            }    */
            this.repositorio.modificar(entregaSecadero);
            this.repositorio.confirmarTransaccion();
        } else {
            this.repositorio.descartarTransaccion();
        }
    }

    public int eliminarEntregaSecadero(Long idEmpleado) {
        this.repositorio.iniciarTransaccion();
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);
        // como se soluciona??
       /* if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(empleado);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {        */
            this.repositorio.descartarTransaccion();
            return 1;
        //}
    }


}
