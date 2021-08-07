package servicios;
import repositorios.*;
import modelo.Cosecha;
import modelo.Empleado;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Servicio_Cosechas {

    private Repositorio repositorio;

    public Servicio_Cosechas(Repositorio p) {
        this.repositorio = p;
    }

   
    

    // Listar y Buscar 
    public List<Cosecha> listarCosechas() {
        return this.repositorio.buscarTodos(Cosecha.class);
        // cambiar por buscar todos ordenados por
    }

    public Cosecha buscarCosecha(int idCosecha) {
        return this.repositorio.buscar(Cosecha.class, idCosecha);
    }



    
    // ABM Cosecha 

    public void agregarCosecha(Empleado empleado, LocalDate fecha) {
        if (empleado == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();

         // Faltan agregar los parametros
        Cosecha cosecha = new Cosecha();
        this.repositorio.insertar(cosecha);
        this.repositorio.confirmarTransaccion();
    }



   

    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public void editarCosecha(int idCosecha, Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Cosecha cosecha = this.repositorio.buscar(Cosecha.class, idCosecha);
        if (cosecha != null) {
            // cosecha.setApellidos(apellidos);
            // cosecha.setNombres(nombres);

            // implementar comparable o comparator
            // o si el id es unico pueden compararar por id
         /*   if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            }   */
            this.repositorio.modificar(cosecha);
            this.repositorio.confirmarTransaccion();
        } else {
            this.repositorio.descartarTransaccion();
        }
    }

  

    public boolean eliminarCosecha(int id_Cosecha) {
        this.repositorio.iniciarTransaccion();
        Cosecha cosecha = this.repositorio.buscar(Cosecha.class, id_Cosecha);
        // como se soluciona??
       /* if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(empleado);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {  */
            this.repositorio.descartarTransaccion();
            return false;
        //}
    }
    

    
}
