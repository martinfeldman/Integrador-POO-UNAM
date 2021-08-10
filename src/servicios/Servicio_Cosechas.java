package servicios;
import repositorios.*;
import modelo.Cosecha;
import modelo.Cuadro;
import modelo.Empleado;

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

    public void agregarCosecha(Empleado empleado, LocalDate fecha, Cuadro cuadroCosechado, Double kgsCosechados) {

        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL
        if (empleado == null || fecha == null || cuadroCosechado == null || kgsCosechados.toString().trim().length() == 0) {
            throw new IllegalArgumentException("Faltan datos");
        }

        Cosecha cosecha = new Cosecha(empleado, fecha, cuadroCosechado, kgsCosechados);
        
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(cosecha);
        this.repositorio.confirmarTransaccion();
    }



    public boolean modificarCosecha(int idCosecha, Empleado empleadoNuevo, LocalDate fecha, Cuadro cuadroCosechadoNuevo,
     Double kgsCosechados ) {

        //- Exepcion si alguno de los datos(obligatrios) que toma de la Vista esta vacio o es NULL
        if (empleadoNuevo == null || fecha == null || cuadroCosechadoNuevo == null || kgsCosechados.toString().trim().length() == 0  ) {
            throw new IllegalArgumentException("Faltan datos");
        }

        //- buscar la cosecha en la base de datos a partir de su ID 
        Cosecha cosecha = this.repositorio.buscar(Cosecha.class, idCosecha);
        

        //- si regresa un objeto cosecha, se hacen TODAS las modificaciones debidas,
        //- incluyendo las dependencias en otras clases y se inicia una transaccion
        if (cosecha != null) {

        //- dependencias de la modificacion

            var empleadoParaQuitar = cosecha.getEmpleado();
            this.repositorio.iniciarTransaccion();    
            
            //- Solo se cambia el empleado de la Cosecha si es diferente del que ya posee    
            if (! empleadoParaQuitar.equals(empleadoNuevo)) {
        
                empleadoParaQuitar.quitarCosecha(cosecha);;
                empleadoNuevo.agregarCosecha(cosecha); 
        
        //- modificaciones al objeto    
                cosecha.setEmpleado(empleadoNuevo);

        //- persistencias
                this.repositorio.modificar(empleadoParaQuitar);        
                this.repositorio.modificar(empleadoNuevo);        
           
            } else {
                System.out.print("empleadoParaQuitar es igual a empleadoNuevo. Se omite esta modificación.\n");
            }

        //- Solo se cambia el cuadro de la Cosecha si es diferente del que ya posee      
            var cuadroParaQuitar = cosecha.getCuadro();

            if (! cuadroParaQuitar.equals(cuadroCosechadoNuevo)) {

                cosecha.setCuadro(cuadroCosechadoNuevo);
            
            } else {
                System.out.print("cuadroParaQuitar es igual a cuadroNuevo. Se omite esta modificación.\n");
            }


            cosecha.setFecha(fecha);
            cosecha.setKgCosechados(kgsCosechados);

            this.repositorio.modificar(cosecha);
            this.repositorio.confirmarTransaccion();
            return true; 

        //- sino se informa y se retorna modificarObjeto = falso
        } else {
            System.out.print("repositorio.buscar(idCosecha) = NULL \n\n");
            return false; 
        }
    }

  

    public boolean eliminarCosecha(int id_Cosecha) {
        this.repositorio.iniciarTransaccion();
        Cosecha cosecha = this.repositorio.buscar(Cosecha.class, id_Cosecha);
        // como se soluciona??
       /* if (empleado != null && empleado.getProyectos().isEmpty() ) {
            this.repositorio.eliminar(cosecha);
            this.repositorio.confirmarTransaccion();
            return 0;
        } else {  */
            this.repositorio.descartarTransaccion();
            return false;
        //}
    }
    

    
}
