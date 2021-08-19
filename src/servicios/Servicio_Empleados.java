package servicios;
import repositorios.*;
import modelo.Empleado;

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
        
        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
        dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        Empleado empleado = new Empleado(nombres.toUpperCase().trim(), apellidos.toUpperCase().trim(), dni);

        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(empleado);
        this.repositorio.confirmarTransaccion();
    }



    public boolean modificarEmpleado(int idEmpleado, String nombres, String apellidos, String dni) {

        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL 
        if (nombres.trim().length() == 0 || apellidos.trim().length() == 0 || 
        dni.trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        // buscar al empleado en la base de datos a partir de su ID
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);

        //- si regresa un objeto empleado, se hacen TODAS las modificaciones debidas,
        //- incluyendo las dependencias en otras clases y se inicia una transaccion 
        if (empleado != null) {
           
        //- modificaciones al objeto    
            empleado.setNombres(nombres.trim().toUpperCase());
            empleado.setApellidos(apellidos.trim().toUpperCase());
            empleado.setDni(dni.trim());

            this.repositorio.iniciarTransaccion();
            this.repositorio.modificar(empleado);
            this.repositorio.confirmarTransaccion();    
            return true;

        //- sino se informa y se retorna modificarObjeto = falso 
        } else {
            System.out.print("repositorio.buscar(idEmpleado) = NULL \n\n");
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

        // SINO, comienza una transaccion con bd 
        // se da de baja el empleado y se confirma transaccion
        } else {
            this.repositorio.iniciarTransaccion();          

            empleado.setAlta(false);

            this.repositorio.confirmarTransaccion();
            return true;
        
        }
    }

    
}





 /*  si el departamento que se quiere cambiar al empleado es el mismo que se tiene se descarta la transaccion
        // sino, se prosigue a modificarlo.
            /* implementar comparable o comparator o si el id es unico pueden compararar por id
            if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            } */
         