package servicios;

import repositorios.*;

import java.util.ArrayList;
import java.util.List;

import modelo.Empleado;


public class Servicio_departamentos {
    

    public void agregarDepartamento(String nombre) {
        // si se repite pasar a método privado
        if (nombre.trim().length() == 0) {
            // manejar una excepción
            throw new IllegalArgumentException("Nombre sin datos");
        }
        this.repositorio.iniciarTransaccion();
        Departamento departamento = new Departamento(nombre.toUpperCase().trim());
        this.repositorio.insertar(departamento);
        this.repositorio.confirmarTransaccion();
    }

    public void eliminarDepartamento(Long idDepartamento) {
        this.repositorio.iniciarTransaccion();
        Departamento departamento = this.repositorio.buscar(Departamento.class, idDepartamento);
        departamento.setAlta(false);
        this.repositorio.modificar(departamento);
        this.repositorio.confirmarTransaccion();
        /*
        // SI USAN ESTO VER DE CAMBIAR EL METODO A QUE RETORNE int
        this.repositorio.iniciarTransaccion();
        Departamento departamento = this.repositorio.buscar(Departamento.class, idDepartamento);
        // como se soluciona??
        if (departamento != null && departamento.getEmpleados().isEmpty() ) {
            this.repositorio.eliminar(departamento);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {
            this.repositorio.descartarTransaccion();
            return 1;
        }
        */


    public void editarDepartamento(Long idDepartamento, String nombre) {
        this.repositorio.iniciarTransaccion();
        Departamento departamento = this.repositorio.buscar(Departamento.class, idDepartamento);
        if (departamento != null) {
            departamento.setNombre(nombre.toUpperCase().trim());
            this.repositorio.modificar(departamento);
            this.repositorio.confirmarTransaccion();
        } else {
            this.repositorio.descartarTransaccion();
        }
    }
    




    public List<Departamento> listarDepartamentos() {
        var departamentosRepositorio = this.repositorio.buscarTodos(Departamento.class);
        var listado = new ArrayList<Departamento>();
        for (var departamento : departamentosRepositorio) {
            if (departamento.isAlta()) {
                listado.add(departamento);
            }
        }
        return listado;
    }

}
