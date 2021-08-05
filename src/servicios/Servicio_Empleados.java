package servicios;
import repositorios.*;
import modelo.Empleado;

import java.util.ArrayList;
import java.util.List;


public class Servicio_Empleados {

    private Repositorio repositorio;

    public Servicio_Empleados(Repositorio p) {
        this.repositorio = p;
    }

    
    // Listar y Buscar Empleado

    public List<Empleado> listarEmpleados() {
        return this.repositorio.buscarTodos(Empleado.class);
        // cambiar por buscar todos ordenados por
    }

    public Empleado buscarEmpleado(int idEmpleado) {
        return this.repositorio.buscar(Empleado.class, idEmpleado);
    }



    // ABM EMPLEADO

    public void agregarEmpleado(String nombres, String apellidos, String dni) {
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
        dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Empleado empleado = new Empleado(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), dni);
        this.repositorio.insertar(empleado);
        this.repositorio.confirmarTransaccion();
    }





    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public boolean editarEmpleado(int idEmpleado, String nombres, String apellidos, String dni) {

        // Execpion si alguno de los datos que toma de la Vista esta vacio,
        // sino comienza una transaccion para editar 
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
        dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        this.repositorio.iniciarTransaccion();

        // buscar el productor en la base de datos a partir de su ID
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);

        // si regresa un objeto empleado se cambia sus nombres, apellidos y dni
        if (empleado != null) {
            empleado.setApellidos(apellidos);
            empleado.setNombres(nombres);
            empleado.setDni(dni);
            this.repositorio.modificar(empleado);
            this.repositorio.confirmarTransaccion();
            return true;

        /*  si el departamento que se quiere cambiar al empleado es el mismo que se tiene se descarta la transaccion
        // sino, se prosigue a modificarlo.
            /* implementar comparable o comparator o si el id es unico pueden compararar por id
            if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            } */
         
        // sino se descarta transaccion     
        } else {
            this.repositorio.descartarTransaccion();
            return false;
        }
    }






    public boolean eliminarEmpleado(int id_Empleado) {
      // se implementa borrado logico
        
        // buscar el productor en la base de datos a partir de su ID 
        Empleado empleado = this.repositorio.buscar(Empleado.class, (Object) id_Empleado);

        // si bd no retorna objeto es porque no existe, y se cancela transaccion
        if (empleado == null) {
            this.repositorio.descartarTransaccion();
            return false;

        // sino comienza una transaccion con bd 
        // se da de baja el empleado, sus cosechas y se confirma transaccion
        } else {
            this.repositorio.iniciarTransaccion();

            // dar de baja cosechas?
          

            empleado.setAlta(false);

            this.repositorio.confirmarTransaccion();
            return true;
    
            // this.repositorio.eliminar(productor);     
           
        }
    }

   
}
