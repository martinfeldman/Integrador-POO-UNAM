package vistas;
import modelo.Empleado;
import modelo.Productor;
import servicios.Servicio_Cosechas;
import servicios.Servicio_Empleados;
import servicios.Servicio_Productores;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Vista_ABM_Cosecha implements Vista {

    // el servicio con el que se va a comunicar la vista
    private final Servicio_Cosechas servicio;
    private Productor productorSeleccionado;
       
    // objetos de la pantalla
    TableView<Productor> tabla;
    TableColumn<Productor, Integer> columnaId;
    TableColumn<Productor, String> columnaNombres;
    TableColumn<Productor, String> columnaApellidos;
    TableColumn<Productor, String> dni;
    Button botonAgregar, botonEliminar, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;

    public Vista_ABM_Cosecha(Servicio_Cosechas servicio){
        this.servicio = servicio;
    }

    @Override
    public Parent obtenerVista() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
