package servicios;
import modelo.Cosecha;
import modelo.Cuadro;
import modelo.Empleado;
import modelo.Lote;
import modelo.Productor;



public class Servicio_seguimientoEmpleado {
    //  Obtener kilos cosechados por empleado a nivel de = obtenerKgsEmpleado_



    public Servicio_seguimientoEmpleado() {}


    public String obtenerKgsEmpleado_productor(Empleado empleado, Productor productor){

        Double totalKgsCosechados = 0.00;

        //- Sumamos los kgsCosechados en cosechas del empleado con productor = productorParametro
        for(Cosecha cosecha : empleado.getCosechas()){

                if (cosecha.getCuadro().getLote().getProductor().equals(productor)){
                    totalKgsCosechados += cosecha.getKgsCosechados(); 
                    System.out.print("totalKgsCosechados: " + totalKgsCosechados + "\n");
                }
        }

        if (totalKgsCosechados == 0.00) {
            return "Empleado " + empleado.getNombres() + " " + empleado.getApellidos() +  ", (" + empleado.getDni() +") " +
             "no ha realizado ninguna cosecha para el productor " + productor;

        } else {
            return "El empeado cosechó " + totalKgsCosechados.toString() + " kgs. para el productor " + productor;
        }
    }



    public String obtenerKgsEmpleado_lote(Empleado empleado, Lote lote){

        Double totalKgsCosechados = 0.00;

        //- Sumamos los kgsCosechados en cosechas del empleado con lote = loteParametro
        for(Cosecha cosecha : empleado.getCosechas()){

                if (cosecha.getCuadro().getLote().equals(lote)){
                    totalKgsCosechados += cosecha.getKgsCosechados();
                }
        }

        if (totalKgsCosechados == 0.00) {
            return "Empleado " + empleado.getNombres() + " " + empleado.getApellidos() +  ", (" + empleado.getDni() +") " +
             "no ha realizado ninguna cosecha para el lote " + lote;

        } else {
            return "El empeado cosechó " + totalKgsCosechados.toString() + " kgs. para el lote " + lote + ", (" + String.valueOf(lote.getProductor()) +") "  ;
        }
    }



    public String obtenerKgsEmpleado_cuadro(Empleado empleado, Cuadro cuadro){

        Double totalKgsCosechados = 0.00;
        
        //- Sumamos los kgsCosechados en cosechas del empleado con cuadro = cuadroParametro
        for(Cosecha cosecha : empleado.getCosechas()){

                if (cosecha.getCuadro().equals(cuadro)){
                    totalKgsCosechados += cosecha.getKgsCosechados();
                }   
        }

        if (totalKgsCosechados == 0.00) {
            return "Empleado " + empleado.getNombres() + " " + empleado.getApellidos() +  ", (" + empleado.getDni() +") " +
             "no ha realizado ninguna cosecha para cuadro " + cuadro;

            } else {
                return "El empeado cosechó " + totalKgsCosechados.toString() + " kgs. en el cuadro " + cuadro + ", (" + String.valueOf(cuadro.getLote().getProductor()) +", " + ", (" + String.valueOf(cuadro.getLote().getProductor()) +") ";
            }
    }
    
    
}
