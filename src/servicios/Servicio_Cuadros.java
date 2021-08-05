package servicios;
import repositorios.*;
import modelo.Cuadro;
import modelo.Empleado;
import modelo.Lote;
import modelo.Productor;

import java.util.ArrayList;
import java.util.List;


public class Servicio_Cuadros{
  
    private Repositorio repositorio;

    public Servicio_Cuadros(Repositorio p) {
        this.repositorio = p;
    }



    // agregarCuadroALote(){
        
    // }





    // CUADRO 
    // Listar y Buscar

    public List<Cuadro> listarCuadros() {
        return this.repositorio.buscarTodos(Cuadro.class);
        // cambiar por buscar todos ordenados por
    }

    public Cuadro buscarCuadro(int idCuadro) {
        return this.repositorio.buscar(Cuadro.class, idCuadro);
    }



    // ABM CUADRO 

    // para agregar cuadro hace falta productor y lote 
    public void agregarCuadro(Productor productor, Lote lote, double Superficie) {
        // falta si superficie esta vacio
        if (productor == null || lote == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();

        // Faltan agregar los parametros
        Cuadro cuadro = new Cuadro();
        this.repositorio.insertar(cuadro);
        this.repositorio.confirmarTransaccion();
    }

 

   
    public boolean editarCuadro(int idCuadro, Productor productor, Lote lote , double superficie ) {
        
        // Execpion si alguno de los datos que toma de la Vista esta vacio,
        // sino comienza una transaccion para editar 
        // falta si superficie esta vacio
        if (productor == null || lote == null) {
            throw new IllegalArgumentException("Faltan datos");
        }
        this.repositorio.iniciarTransaccion();
        Cuadro cuadro = this.repositorio.buscar(Cuadro.class, idCuadro);

        // solo realizar la modificacion si se corrobora que el lote ingresado pertenece al productor ingresado
        if (cuadro != null) {
            cuadro.setProductor(productor);
            cuadro.setLote(lote);
            cuadro.setSuperficie(superficie);
            // implementar comparable o comparator
            // o si el id es unico pueden compararar por id
          /*  if (! empleado.getDepartamento().equals(departamento)) {
                empleado.getDepartamento().getEmpleados().remove(empleado);
                empleado.setDepartamento(departamento);
                departamento.getEmpleados().add(empleado);
            }*/
            this.repositorio.modificar(cuadro);
            this.repositorio.confirmarTransaccion();
            return true;
        } else {
            this.repositorio.descartarTransaccion();
            return false;
        }
    }



    // cambiar valor devuelto (por ejemplo: True ok, False problemas)
    public int eliminarCuadro(int idEmpleado) {
        this.repositorio.iniciarTransaccion();
        Empleado empleado = this.repositorio.buscar(Empleado.class, idEmpleado);
        return 1; 
      
      /*  // como se soluciona??
        if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(empleado);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {
            this.repositorio.descartarTransaccion();
            return 1;
        } */
    }

   
}
