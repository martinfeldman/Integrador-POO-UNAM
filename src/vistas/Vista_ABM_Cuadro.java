package vistas;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Cuadro;
import modelo.Lote;
import modelo.Productor;
import servicios.Servicio_Cuadros;
import servicios.Servicio_Lotes;
import servicios.Servicio_Productores;



public class Vista_ABM_Cuadro implements Vista {

    // el servicio con el que se va a comunicar la vista
    private final Servicio_Cuadros servicio;
    Servicio_Productores servicio_Productores;
    Servicio_Lotes servicio_Lotes;
    private Cuadro cuadroSeleccionado;

    // objetos de la pantalla

    Button botonAgregar, botonEliminar, botonLimpiar;
    ComboBox<Lote> lotesBox;
    Label etiquetaInteractiva, etiqueta_productor, etiquetaComboBox_lotes;
    TableView<Cuadro> tabla;
    TableColumn<Cuadro, Integer> columnaId;
    TableColumn<Cuadro, Lote> columnaLote;
    TableColumn<Cuadro, Double> columnaSuperficie;
    TextField entradaSuperficie;
    Separator separador1, separador2, separador3 ; 
    
        
    public Vista_ABM_Cuadro(Servicio_Cuadros servicio, Servicio_Productores servicio_Productores, Servicio_Lotes servicio_Lotes) {
        this.servicio = servicio;
        this.servicio_Productores =  servicio_Productores;
        this.servicio_Lotes = servicio_Lotes; 
    }

    
    @Override
    public Parent obtenerVista() {


    // definicion elementos de pantalla 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id de Cuadro");
        columnaLote = new TableColumn<>("Lote");
        columnaSuperficie = new TableColumn<>("Superficie");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
        HBox contenedorBox = new HBox();
        HBox contenedorHorizontal1 = new HBox();
        HBox contenedorHorizontal2 = new HBox();

        entradaSuperficie = new TextField("");

        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaComboBox_lotes = new Label ("Seleccione el lote al que pertenece el cuadro   ");
        etiqueta_productor = new Label("");

        lotesBox = new ComboBox<>();

        separador1 = new Separator(Orientation.VERTICAL);
        separador2 = new Separator(Orientation.HORIZONTAL); 
     
        tabla = new TableView<>(); 

     

    // propiedades de elementos

        //- propiedades de COLUMNAS
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idCuadro"));
        columnaLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columnaSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));

        columnaId.setMinWidth(200);
        columnaLote.setMinWidth(200);
        columnaSuperficie.setMinWidth(200);

        contenedor.setAlignment(Pos.CENTER);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorCarga.setAlignment(Pos.CENTER);

        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));

        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);

        entradaSuperficie.setPromptText("Ingrese Superficie en km²");
        entradaSuperficie.setMaxWidth(300);
        
        separador1.setPadding(new Insets(0,40,0,40));
        separador2.setPadding(new Insets(40,0,40,0));

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPadding(new Insets(0, 0, 10, 0));

       



    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarCuadro());
        botonEliminar.setOnAction(e -> clicEliminarCuadro());
        botonLimpiar.setOnAction(e -> limpiar());
        lotesBox.setOnAction(e -> cambiarEtiquetaProductor());

        //- cargamos datos a la tabla y los comboBoxs, a partir de consultas a la BD 
        tabla.getItems().addAll(this.servicio.listarCuadros());

        lotesBox.getItems().addAll(servicio_Lotes.listarLotes());


        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaLote);
        tabla.getColumns().add(columnaSuperficie);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva);
        contenedorBox.getChildren().addAll(etiquetaComboBox_lotes, lotesBox, separador1, etiqueta_productor);
       // contenedorHorizontal1.getChildren().addAll(
        //contenedorHorizontal2.getChildren().addAll(
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorBox, separador2, entradaSuperficie, contenedorBotones);

        return contenedor;

    }



    private void clicAgregarCuadro() {

        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();

        try {

            //- Si no hay elemento seleccionado en la tabla, se tiene un nuevo objeto por agregar
            if (cuadroSeleccionado == null) {
                servicio.agregarCuadro(lotesBox.getValue(), Double.parseDouble(entradaSuperficie.getText()));


            //- SINO, modificamos el cuadroSeleccionado a partir de su id
            } else {
                servicio.modificarCuadro(cuadroSeleccionado.getIdCuadro(), lotesBox.getValue(), Double.parseDouble(entradaSuperficie.getText()));
            }

            limpiar();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
   
        }
    }


    private void cargarDatos() {

        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (cuadroSeleccionado != null) {

            entradaSuperficie.setText(String.valueOf(cuadroSeleccionado.getSuperficie()));
            etiquetaInteractiva.setText("Está seleccionado el Cuadro con id: " + cuadroSeleccionado.getIdCuadro());
            lotesBox.setValue(cuadroSeleccionado.getLote());
        }
    }


 
    private void clicEliminarCuadro() {

        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (cuadroSeleccionado != null) {
            servicio.eliminarCuadro(cuadroSeleccionado.getIdCuadro());
            limpiar();
        }
    }

    

    private void limpiar() {

    // limpiar elementos de la vista
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        etiqueta_productor.setText("");
        entradaSuperficie.clear();
        
        lotesBox.getSelectionModel().clearSelection();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCuadros());
    } 



    private void cambiarEtiquetaProductor() {

        if (lotesBox.getValue() != null){

            etiqueta_productor.setText("Este lote pertenece al productor \n" + lotesBox.getValue().getProductor().toString());
            
        } else {

        }
    }
}
