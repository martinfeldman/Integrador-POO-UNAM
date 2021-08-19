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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import modelo.Cuadro;
import modelo.Lote;
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
    Font fuenteNegrita;
    Label etiquetaInteractiva, etiquetaInteractiva2, etiqueta_productor, etiquetaComboBox_lotes;
    TableView<Cuadro> tabla;
    TableColumn<Cuadro, Integer> columnaId;
    TableColumn<Cuadro, Lote> columnaLote;
    TableColumn<Cuadro, Double> columnaSuperficie;
    TableColumn<Cuadro, Boolean> columnaAlta;
    TextField entradaSuperficie;
    Separator separador1V, separador1H, separador2H; 
    
        
    public Vista_ABM_Cuadro(Servicio_Cuadros servicio, Servicio_Productores servicio_Productores, Servicio_Lotes servicio_Lotes) {
        this.servicio = servicio;
        this.servicio_Productores =  servicio_Productores;
        this.servicio_Lotes = servicio_Lotes; 
    }

    
    @Override
    public Parent obtenerVista() {

    
    // definicion elementos de pantalla 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Dar de baja");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id de Cuadro");
        columnaLote = new TableColumn<>("Lote");
        columnaSuperficie = new TableColumn<>("Superficie");
        columnaAlta = new TableColumn<>("Alta");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
        HBox contenedorBox = new HBox();

        entradaSuperficie = new TextField("");

        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para modificarlas (si su estado de alta es Verdadero)");
        etiquetaInteractiva2 = new Label("");
        etiquetaComboBox_lotes = new Label ("Seleccione el lote al que pertenece el cuadro   ");
        etiqueta_productor = new Label("");

        lotesBox = new ComboBox<>();

        separador1H = new Separator(Orientation.HORIZONTAL); 
        separador2H = new Separator(Orientation.HORIZONTAL); 
        separador1V = new Separator(Orientation.VERTICAL);
        
     
        tabla = new TableView<>(); 

     

    // propiedades de elementos

        //- propiedades de COLUMNAS
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idCuadro"));
        columnaLote.setCellValueFactory(new PropertyValueFactory<>("lote"));
        columnaSuperficie.setCellValueFactory(new PropertyValueFactory<>("superficie"));
        columnaAlta.setCellValueFactory(new PropertyValueFactory<>("alta"));

        columnaId.setMinWidth(200);
        columnaLote.setMinWidth(200);
        columnaSuperficie.setMinWidth(200);
        columnaAlta.setMinWidth(100);

        contenedor.setAlignment(Pos.CENTER);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorCarga.setAlignment(Pos.CENTER);

        contenedorBotones.setPadding(new Insets(20, 10, 30, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 50, 10));

        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);

        entradaSuperficie.setPromptText("Ingrese Superficie en km²");
        entradaSuperficie.setMaxWidth(300);

        fuenteNegrita = Font.font("", FontWeight.BOLD, FontPosture.REGULAR , 15);
        etiquetaInteractiva2.setFont(fuenteNegrita);
        

        lotesBox.setMinWidth(100);
        lotesBox.setPrefWidth(100);
        
        separador1H.setPadding(new Insets(0, 0, 0, 0));
        separador2H.setPadding(new Insets(20, 0, 20, 0));
        separador1V.setPadding(new Insets(0, 10, 0, 20));

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPadding(new Insets(0, 0, 10, 0));
        tabla.setPrefHeight(400);
        tabla.setPrefWidth(700);

       



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
        tabla.getColumns().add(columnaAlta);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva, separador1H);
        contenedorBox.getChildren().addAll(etiquetaComboBox_lotes, lotesBox, separador1V, etiqueta_productor);
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorBox, separador2H, entradaSuperficie, contenedorBotones, etiquetaInteractiva2);

        return contenedor;

    }



    private boolean clicAgregarCuadro() {

        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();

        try {

            //- Si no hay elemento seleccionado en la tabla, se tiene un nuevo objeto por agregar
            if (cuadroSeleccionado == null) {
                servicio.agregarCuadro(lotesBox.getValue(), Double.parseDouble(entradaSuperficie.getText()));


            //- SINO, si el cuadroSeleccionado está de Alta, podremos modificarlo a partir de su id
            } else {

                if (cuadroSeleccionado.isAlta() == true) {
                    servicio.modificarCuadro(cuadroSeleccionado.getIdCuadro(), lotesBox.getValue(), Double.parseDouble(entradaSuperficie.getText()));

            //- SINO, se informa y se retorna falso    
                } else {
                    
                    limpiar();
                    etiquetaInteractiva2.setText("El cuadro seleccionado está dado de BAJA. No se puede modificar.\n"); 
                    return false;
                }    

            }

            limpiar();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
   
        }

        return true; 
    }



    private void cargarDatos() {

        cuadroSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (cuadroSeleccionado != null) {

            etiquetaInteractiva.setText("Está seleccionado el Cuadro con id: " + cuadroSeleccionado.getIdCuadro());
            entradaSuperficie.setText(String.valueOf(cuadroSeleccionado.getSuperficie()));
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
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para modificarlas (si su estado de alta es Verdadero)");
        etiquetaInteractiva2.setText("");
        etiqueta_productor.setText("");
        entradaSuperficie.clear();
        
        lotesBox.getSelectionModel().clearSelection();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCuadros());
    } 



    private void cambiarEtiquetaProductor() {

        if (lotesBox.getValue() != null){

            etiqueta_productor.setText("Este lote pertenece al productor " + lotesBox.getValue().getProductor().toString());
            
        } else {

        }
    }
}
