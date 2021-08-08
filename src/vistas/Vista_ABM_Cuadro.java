package vistas;
import modelo.Cuadro;
import modelo.Lote;
import modelo.Productor;
import servicios.Servicio_Cuadros;
import servicios.Servicio_Lotes;
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



public class Vista_ABM_Cuadro implements Vista {

    // el servicio con el que se va a comunicar la vista
    private final Servicio_Cuadros servicio;
    Servicio_Productores servicio_Productores;
    Servicio_Lotes servicio_Lotes;
    private Cuadro cuadroSeleccionado;

    // objetos de la pantalla
    TableView<Cuadro> tabla;
    TableColumn<Cuadro, Integer> columnaId;
    TableColumn<Cuadro, Lote> columnaLote;
    TableColumn<Cuadro, Productor> columnaProductor;
    TableColumn<Cuadro, Double> columnaSuperficie;
    Button botonAgregar, botonEliminar, botonLimpiar;
    ComboBox<Lote> lotesBox;
    Label etiquetaId, etiquetaInteractiva, etiqueta_productor, etiquetaComboBox_lotes;
    TextField entradaSuperficie;
    
    
    public Vista_ABM_Cuadro(Servicio_Cuadros servicio, Servicio_Productores servicio_Productores, Servicio_Lotes servicio_Lotes) {
        this.servicio = servicio;
        this.servicio_Productores =  servicio_Productores;
        this.servicio_Lotes = servicio_Lotes; 
    }

    
    @Override
    public Parent obtenerVista() {


    // definicion elementos de pantalla 

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
     
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaComboBox_lotes = new Label ("Seleccione el lote al que pertenece el cuadro");
        etiqueta_productor = new Label("");

        entradaSuperficie = new TextField("");
        lotesBox = new ComboBox<>();
        tabla = new TableView<>(); 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id");
        columnaLote = new TableColumn<>("Lote");
        columnaProductor = new TableColumn<>("Productor");
        columnaSuperficie = new TableColumn<>("Superficie");
       

    // propiedades de elementos

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPrefHeight(300);

        entradaSuperficie.setPromptText("Ingrese Superficie:   ");
        
        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        
        //- propiedades de COLUMNAS
        columnaId.setMinWidth(100);
        columnaLote.setMinWidth(300);
        columnaProductor.setMinWidth(300);
        columnaSuperficie.setMinWidth(300);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Cuadro"));
        columnaProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));
        columnaLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columnaSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));

   

    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarCuadro());
        botonEliminar.setOnAction(e -> clicEliminarCuadro());
        botonLimpiar.setOnAction(e -> limpiar());
        lotesBox.setOnAction(e -> cambiarEtiquetaProductor());

        lotesBox.getItems().addAll(servicio_Lotes.listarLotes());
        tabla.getItems().addAll(this.servicio.listarCuadros());


        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaProductor);
        tabla.getColumns().add(columnaLote);
        tabla.getColumns().add(columnaSuperficie);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva ,etiquetaId, etiquetaComboBox_lotes, lotesBox, etiqueta_productor, entradaSuperficie);
        contenedor.getChildren().addAll(tabla, contenedorCarga);

        return contenedor;

    }









    private void clicAgregarCuadro() {
        // asumo selección simple
        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();
        try {
            if (cuadroSeleccionado == null) {
                // Si no hay elemento seleccionado en la tabla
                // Faltan completar los parametros
                servicio.agregarCuadro(lotesBox.getValue().getProductor(), lotesBox.getValue(), Double.parseDouble(entradaSuperficie.getText()) );
            } else {
                // SINO modificar el cuadro
                //servicio.editarEmpleado(Long.parseLong(etiquetaIdEmpleado.getText()), entradaNombres.getText(), entradaApellidos.getText(), departamentos.getSelectionModel().getSelectedItem());
            }
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }


    private void cargarDatos() {
        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (cuadroSeleccionado != null) {
            etiquetaId.setText(String.valueOf(cuadroSeleccionado.getId_Cuadro()));
            //entradaNombres.setText(cuadroSeleccionado.getNombres());
           // entradaApellidos.setText(cuadroSeleccionado.getApellidos());
           // departamentos.getSelectionModel().select(cuadroSeleccionado.getDepartamento());
        }
    }


 
    private void clicEliminarCuadro() {
        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (cuadroSeleccionado != null) {
            servicio.eliminarCuadro(cuadroSeleccionado.getId_Cuadro());
            limpiar();
        }
    }

    
    private void limpiar() {
        etiquetaId.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        entradaSuperficie.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCuadros());
    } 


    private void cambiarEtiquetaProductor() {
        etiqueta_productor.setText("Este lote pertenece al productor \n" + lotesBox.getValue().getProductor().toString());
    }
}
