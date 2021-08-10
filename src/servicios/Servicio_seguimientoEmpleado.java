package servicios;
import repositorios.*;
import modelo.Cuadro;
import modelo.Empleado;
import modelo.Lote;
import modelo.Productor;

import java.util.ArrayList;
import java.util.List;

public class Servicio_seguimientoEmpleado {
    //  Obtener kilos cosechados por empleado a nivel de = obtenerKgsEmpleado_

    private Repositorio repositorio;

    public Servicio_seguimientoEmpleado(Repositorio p) {
        this.repositorio = p;
    }


    public double obtenerKgsEmpleado_productor(Empleado empleado, Productor productor){
        

        return 3.9 ;
    }

    public double obtenerKgsEmpleado_lote(Empleado empleado, Lote lote){

        return 3.9 ;
    }

    public double obtenerKgsEmpleado_cuadro(Empleado empleado, Cuadro cuadro){
        
        return 3.9 ;
    }
    
}
