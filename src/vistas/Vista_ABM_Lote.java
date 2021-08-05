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
    Servicio_Productores servicio_productor;
    private Lote loteSeleccionado;

    // objetos de la pantalla
    TableView<Lote> tabla;
    TableColumn<Lote, Integer> columnaId;
 
    Button botonAgregar, botonEliminar, botonLimpiar;
    ComboBox<Productor> productoresBox;
    Label etiquetaId, etiquetaInteractiva;

    

    public Vista_ABM_Lote(Servicio_Lotes servicio) {
        this.servicio = servicio;
    }

    @Override
    public Parent obtenerVista() {

        // creamos elementos  de pantalla 
        // labels & textFields & comboBox
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");

        var productoresBox = new ComboBox<>();
        productoresBox.getItems().addAll(servicio_productor.listarProductores());


       //  tres botones principales y asociamos eventos
        
        botonAgregar = new Button("Agregar");
        botonAgregar.setOnAction(e -> clicAgregarEmpleado());

        botonEliminar = new Button("Eliminar");
        botonEliminar.setOnAction(e -> clicEliminarEmpleado());

        botonLimpiar = new Button("Limpiar");
        botonLimpiar.setOnAction(e -> limpiar());



        // contenedor para los botones
        HBox contenedorBotones = new HBox();
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorBotones.setSpacing(10);


        // agregamos al contenedor las entradas y botones
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);


        // creamos un contenedor para la carga
        VBox contenedorCarga = new VBox();
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setSpacing(10);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva, etiquetaId, productoresBox);



        // COLUMNAS y sus propiedades
        var columnaIdEmpleado = new TableColumn<>("Id");
        columnaIdEmpleado.setMinWidth(100);
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("id_Lote"));

        var columnaProductor = new TableColumn<>("Productor");
        columnaIdEmpleado.setMinWidth(100);
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("productor"));

        // como mostrar los cuadros de un lote? 
        // Podria crear otra tabla debajo que lea el loteSeleccionado 
        // en la tabla de arriba y haga la busqueda.



        // TABLA
        var tabla = new TableView<>();
        // opcional (como es la seleccion: unica, multiple, etc)
        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);




        // pedimos los datos de Empleados a la BD y mostramos en tabla
        tabla.getItems().addAll(this.servicio.listarLotes());


        // agregamos las columnas a la tabla     -- INCOMPLETO
        tabla.getColumns().add(columnaIdEmpleado);
        // COMPLETAR ACA
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());


        // Contenedor pincipal:  para la tabla y contenedor de carga
        VBox contenedor = new VBox();
        // agregamos al contenedor la tabla
        contenedor.getChildren().addAll(tabla, contenedorCarga);

        return contenedor;

    }


    

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
        var tabla = new TableView<>();

        // quitamos la selección en la tabla
        tabla.getSelectionModel().clearSelection();

        etiquetaId.setText("");
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarLotes());
    } 


}
