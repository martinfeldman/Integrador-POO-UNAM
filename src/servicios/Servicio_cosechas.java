package servicios;
import repositorios.*;
import modelo.Empleado;

import java.util.ArrayList;
import java.util.List;


public class Servicio_cosechas {

    private Repositorio repositorio;

    public Servicio_cosechas(Repositorio p) {
        this.repositorio = p;
    }

     // obtener diferencia de kgs entre el peso registrado en el campo y en el secadero 
     public double diffCampoSecadero(){
        return 3.9;
    }
    
    // Cosecha
    // Listar y Buscar 
    public List<Empleado> listarCosechas() {
        return this.repositorio.buscarTodos(Empleado.class);
        // cambiar por buscar todos ordenados por
    }

    public Empleado buscarCosecha(Long idEmpleado) {
        return this.repositorio.buscar(Empleado.class, idEmpleado);
    }

    // ABM Cosecha 

    public void agregarCosecha(String nombres, String apellidos, Departamento departamento) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
            departamento == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = new Empleado(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), departamento);
        this.repositorio.insertar(empleado);
        this.repositorio.confirmarTransaccion();
    }

    public int eliminarCosecha(Long idEmpleado) {
        this.repositorio.iniciarTransaccion();
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);
        // como se soluciona??
        if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(empleado);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {
            this.repositorio.descartarTransaccion();
            return 1;
        }
    }

    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public void editarCosecha(Long idEmpleado, String nombres, String apellidos, Departamento departamento) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
            departamento == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);
        if (empleado != null) {
            empleado.setApellidos(apellidos);
            empleado.setNombres(nombres);
            // implementar comparable o comparator
            // o si el id es unico pueden compararar por id
            if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            }
            this.repositorio.modificar(empleado);
            this.repositorio.confirmarTransaccion();
        } else {
            this.repositorio.descartarTransaccion();
        }
    }

  


    // ---------------------------------------------------------------------------------------------------
    //Entrega Cosecha Secadero

    // Listar y Buscar 
    public List<Empleado> listarEntregaSecadero() {
        return this.repositorio.buscarTodos(Empleado.class);
        // cambiar por buscar todos ordenados por
    }

    public Empleado buscarEntregaSecadero(Long idEmpleado) {
        return this.repositorio.buscar(Empleado.class, idEmpleado);
    }

 

   // ABM Entrega Cosecha Secadero 

   public void agregarEntregaSecadero(String nombres, String apellidos, Departamento departamento) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
        departamento == null) {
        throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = new Empleado(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), departamento);
        this.repositorio.insertar(empleado);
        this.repositorio.confirmarTransaccion();
    }

    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public void editarEntregaSecadero(Long idEmpleado, String nombres, String apellidos, Departamento departamento) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
            departamento == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);
        if (empleado != null) {
            empleado.setApellidos(apellidos);
            empleado.setNombres(nombres);
            // implementar comparable o comparator
            // o si el id es unico pueden compararar por id
            if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            }
            this.repositorio.modificar(empleado);
            this.repositorio.confirmarTransaccion();
        } else {
            this.repositorio.descartarTransaccion();
        }
    }

    public int eliminarEntregaSecadero(Long idEmpleado) {
        this.repositorio.iniciarTransaccion();
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);
        // como se soluciona??
        if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(empleado);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {
            this.repositorio.descartarTransaccion();
            return 1;
        }
    }


}
