package vistas;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Empleado;
import modelo.Lote;
import modelo.Productor;
import servicios.Servicio_Lotes;
import servicios.Servicio_Productores;

// para esta vista se utilizó como referencia la vista de empleado del ejemploIntegrador

public class Vista_ABM_Lote implements Vista {
    
    // el servicio con el que se va a comunicar la vista
    private final Servicio_Lotes servicio;
    Servicio_Productores servicio_Productores;
    private Lote loteSeleccionado;

    // objetos de la pantalla
    TableView<Lote> tabla;
    TableColumn<Lote, Integer> columnaId;
    TableColumn<Lote, Productor> columnaProductor;
 
    Button botonAgregar, botonEliminar, botonLimpiar;
    ComboBox<Productor> productoresBox;
    Label etiquetaId, etiquetaInteractiva;

    

    public Vista_ABM_Lote(Servicio_Lotes servicio, Servicio_Productores servicio_Productores) {
        this.servicio = servicio;
        this.servicio_Productores = servicio_Productores;
    }

    @Override
    public Parent obtenerVista() {

        // definicion elementos de pantalla
      
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        
        productoresBox = new ComboBox<>();

        botonAgregar = new Button("Agregar");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();

        columnaId = new TableColumn<>("Id");
        columnaProductor = new TableColumn<>("Productor");

        tabla = new TableView<>();
        

        // propiedades de elementos

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);

        // COLUMNAS - propiedades
        columnaId.setMinWidth(100);
        columnaProductor.setMinWidth(100);
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Lote"));
        columnaProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));

        

        // acciones sobre elementos 

        botonAgregar.setOnAction(e -> clicAgregarEmpleado());
        botonEliminar.setOnAction(e -> clicEliminarEmpleado());
        botonLimpiar.setOnAction(e -> limpiar());

        productoresBox.getItems().addAll(servicio_Productores.listarProductores());       

        // pedimos los datos de Empleados a la BD y mostramos en tabla
        tabla.getItems().addAll(this.servicio.listarLotes());

        // agregamos las columnas a la tabla     -- COMPLETO?
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaProductor);
        // COMPLETAR ACA
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
       
        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva, etiquetaId, productoresBox);
        contenedor.getChildren().addAll(tabla, contenedorCarga);
        

        return contenedor;

    }

        // como mostrar los cuadros de un lote? 
        // Podria crear otra tabla debajo que lea el loteSeleccionado 
        // en la tabla de arriba y haga la busqueda.

    

    private void clicAgregarEmpleado() {
        // asumo selección simple
        loteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        try {
            if (loteSeleccionado == null) {
                // Si no hay elemento seleccionado en la tabla
                servicio.agregarLote(productoresBox.getSelectionModel().getSelectedItem());
            } else {
                // cambiamos el empleado
                servicio.editarLote(Integer.parseInt(etiquetaId.getText()), productoresBox.getSelectionModel().getSelectedItem());
            }
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }



     // A partir de la seleccion de un objeto sobre la tabla, Se cargan sus datos en los elementos de la pantalla
    private void cargarDatos() {
        loteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (loteSeleccionado != null) {
            etiquetaId.setText(String.valueOf(loteSeleccionado.getId_Lote()));
            productoresBox.getSelectionModel().select(loteSeleccionado.getProductor());
        }
    }



    private void clicEliminarEmpleado() {
        loteSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (loteSeleccionado != null) {
            servicio.eliminarLote(loteSeleccionado.getId_Lote());
            limpiar();
        }
    }



     // limpiar vista 
     private void limpiar() {
        etiquetaId.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarLotes());
    } 


}
