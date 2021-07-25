package servicios;
import repositorios.*;
import modelo.Empleado;

import java.util.ArrayList;
import java.util.List;


public class Servicio_CuadroLote {
  
    private Repositorio repositorio;

    public Servicio_CuadroLote(Repositorio p) {
        this.repositorio = p;
    }

    // CUADRO 
    // Listar y Buscar

    public List<Empleado> listarCuadros() {
        return this.repositorio.buscarTodos(Empleado.class);
        // cambiar por buscar todos ordenados por
    }

    public Empleado buscarCuadro(Long idEmpleado) {
        return this.repositorio.buscar(Empleado.class, idEmpleado);
    }


    // ABM CUADRO 

    public void agregarCuadro(String nombres, String apellidos, Departamento departamento) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
            departamento == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = new Empleado(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), departamento);
        this.repositorio.insertar(empleado);
        this.repositorio.confirmarTransaccion();
    }

    public int eliminarEmpleado(Long idEmpleado) {
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
    public boolean editarEmpleado(Long idEmpleado, String nombres, String apellidos, Departamento departamento) {
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
            return true;
        } else {
            this.repositorio.descartarTransaccion();
            return false;
        }
    }



    // LOTE
    // Listar y Buscar

    public List<Empleado> listarLotes() {
        return this.repositorio.buscarTodos(Empleado.class);
        // cambiar por buscar todos ordenados por
    }

    public Empleado buscarLote(Long idEmpleado) {
        return this.repositorio.buscar(Empleado.class, idEmpleado);
    }
    

    // ABM LOTE 

    public void agregarLote(String nombres, String apellidos, Departamento departamento) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
            departamento == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = new Empleado(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), departamento);
        this.repositorio.insertar(empleado);
        this.repositorio.confirmarTransaccion();
    }

    public int eliminarLote(Long idEmpleado) {
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
    public boolean editarLote(Long idEmpleado, String nombres, String apellidos, Departamento departamento) {
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
            return true;
        } else {
            this.repositorio.descartarTransaccion();
            return false;
        }
    }
}
