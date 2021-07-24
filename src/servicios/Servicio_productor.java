package servicios;
import repositorios.*;
import modelo.Empleado;

import java.util.ArrayList;
import java.util.List;


public class Servicio_productor {

    private Repositorio repositorio;

    public Servicio_productor(Repositorio p) {
        this.repositorio = p;
    }

    // Listar y Buscar Productor

    public List<Empleado> listarProductor() {
        return this.repositorio.buscarTodos(Empleado.class);
        // cambiar por buscar todos ordenados por
    }

    public Empleado buscarProductor(Long idEmpleado) {
        return this.repositorio.buscar(Empleado.class, idEmpleado);
    }




    // ABM PRODUCTOR 

    public void agregarProductor(String nombres, String apellidos, Departamento departamento) {
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
    public boolean editarProductor(Long idEmpleado, String nombres, String apellidos, Departamento departamento) {
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

    public int eliminarProductor(Long idEmpleado) {
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
