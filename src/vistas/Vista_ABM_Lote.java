package vistas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
 
    Button botonAgregar, botonEliminar, botonLimpiar;
    ComboBox<Productor> productoresBox;
    Label etiquetaInteractiva;
    TableView<Lote> tabla;
    TableColumn<Lote, Integer> columnaId;
    TableColumn<Lote, Productor> columnaProductor;


    public Vista_ABM_Lote(Servicio_Lotes servicio, Servicio_Productores servicio_Productores) {
        this.servicio = servicio;
        this.servicio_Productores = servicio_Productores;
    }



    @Override
    public Parent obtenerVista() {


    // definicion elementos de pantalla
      
        botonAgregar = new Button("Agregar");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id de Lote");
        columnaProductor = new TableColumn<>("Productor");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();

        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        
        productoresBox = new ComboBox<>();

        tabla = new TableView<>();
        


    // propiedades de elementos

        // COLUMNAS - propiedades
        columnaId.setMinWidth(200);
        columnaProductor.setMinWidth(300);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        columnaProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));

        contenedor.setAlignment(Pos.CENTER);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorCarga.setAlignment(Pos.CENTER);

        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setSpacing(10);
        contenedorBotones.setSpacing(10);
      
        productoresBox.setPromptText("Ingrese productor");
        productoresBox.setMinWidth(200);

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPadding(new Insets(0, 0, 10, 0));
        tabla.setPrefHeight(300);




    // acciones sobre elementos 

        botonAgregar.setOnAction(e -> clicAgregarLote());
        botonEliminar.setOnAction(e -> clicEliminarLote());
        botonLimpiar.setOnAction(e -> limpiar());
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- cargamos datos a la tabla y los comboBoxs, a partir de consultas a la BD 
        tabla.getItems().addAll(this.servicio.listarLotes());

        productoresBox.getItems().addAll(servicio_Productores.listarProductores());       


        //- agregamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaProductor);
        
        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva, productoresBox);
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorBotones);
        
        return contenedor;
    }

    

    private void clicAgregarLote() {

        loteSeleccionado = tabla.getSelectionModel().getSelectedItem();

        try {

            //- Si no hay elemento seleccionado en la tabla, se tiene un nuevo objeto por agregar
            if (loteSeleccionado == null) {

                servicio.agregarLote(productoresBox.getSelectionModel().getSelectedItem());


            //- SINO, modificamos el loteSeleccionado a partir de su id
            } else {
                servicio.modificarLote(loteSeleccionado.getIdLote(), productoresBox.getSelectionModel().getSelectedItem());
            }

            limpiar();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }



    private void cargarDatos() {
        
        // A partir de la seleccion de un objeto sobre la tabla, Se cargan sus datos en los elementos de la pantalla
        loteSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (loteSeleccionado != null) {

            etiquetaInteractiva.setText("Está seleccionado el Lote con id: " + loteSeleccionado.getIdLote());

            productoresBox.getSelectionModel().select(loteSeleccionado.getProductor());
        }
    }



    private void clicEliminarLote() {

        loteSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (loteSeleccionado != null) {

            servicio.eliminarLote(loteSeleccionado.getIdLote());
            limpiar();
        }
    }



    private void limpiar() {

        //- limpiar elementos de la vista
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");

        productoresBox.getSelectionModel().clearSelection();
        productoresBox.setPromptText("Ingrese productor");

        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarLotes());
    } 


}
