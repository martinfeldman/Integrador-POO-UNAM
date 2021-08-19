package vistas;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import modelo.Cosecha;
import modelo.EntregaSecadero;
import servicios.Servicio_Cosechas;
import servicios.Servicio_EntregasSecadero;

public class Vista_ABM_EntregaSecadero implements Vista {
    
    // el servicio con el que se va a comunicar la vista
    private final Servicio_EntregasSecadero servicio;
    Servicio_Cosechas servicio_Cosechas;
    private EntregaSecadero entregaSeleccionada;
       
    // objetos de la pantalla
   
    Button botonAgregar, botonEliminar, botonLimpiar;
    ComboBox<Cosecha> cosechasBox;
    DatePicker datepicker;
    Font fuenteNegrita;
    Label etiquetaInteractiva, etiquetaInteractiva2, etiquetaComboBox_cosechas, etiquetaFecha, etiquetaPesoSecadero;
    TableView<EntregaSecadero> tabla;
    TableColumn<EntregaSecadero, Integer> columnaId;
    TableColumn<EntregaSecadero, LocalDate> columnaFecha;
    TableColumn<EntregaSecadero, ArrayList<Cosecha>> columnaCosecha;
    TableColumn<EntregaSecadero, Double> columnaPesoSecadero;
    TableColumn<EntregaSecadero, Double> columnaPesoCampo;
    TableColumn<EntregaSecadero, Double> columnaDiferenciaPeso;
    TableColumn<EntregaSecadero, Boolean> columnaAlta;
    TextField entradaNombres, entradaApellidos, entradaDni, entradaPesoSecadero; 
    Separator separador1H, separador1V;


    public Vista_ABM_EntregaSecadero(Servicio_EntregasSecadero servicio, Servicio_Cosechas servicio_Cosechas){
        this.servicio = servicio;
        this.servicio_Cosechas = servicio_Cosechas;
    }



    @Override
    public Parent obtenerVista() {

    // definicion elementos de pantalla 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Dar de baja");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id de Entrega Secadero");
        columnaFecha = new TableColumn<>("Fecha");
        columnaCosecha = new TableColumn<>("Cosecha");
        columnaPesoSecadero = new TableColumn<>("Peso Secadero");
        columnaPesoCampo = new TableColumn<>("Peso Campo");
        columnaDiferenciaPeso = new TableColumn<>("Diferencia Peso (S - C)");
        columnaAlta = new TableColumn<>("Alta");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
        HBox contenedorHorizontal = new HBox();

        cosechasBox = new ComboBox<>();

        datepicker = new DatePicker();
        
        entradaPesoSecadero = new TextField();

        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para modificarlas (si su estado de alta es Verdadero)");
        etiquetaInteractiva2 = new Label("");
        etiquetaComboBox_cosechas = new Label ("Seleccione la cosecha que llega en la entrega   ");
        etiquetaFecha = new Label ("Fecha de Entrega:   ");

        fuenteNegrita = Font.font("", FontWeight.BOLD, FontPosture.REGULAR , 15);

        separador1H = new Separator(Orientation.HORIZONTAL);
        separador1V = new Separator(Orientation.VERTICAL);

        tabla = new TableView<>(); 
              
       

    // propiedades de elementos
    
        //- propiedades de COLUMNAS
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idEntrega"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        columnaCosecha.setCellValueFactory(new PropertyValueFactory<>("cosecha"));
        columnaPesoSecadero.setCellValueFactory(new PropertyValueFactory<>("pesoSecadero"));
        columnaPesoCampo.setCellValueFactory(new PropertyValueFactory<>("pesoCampo"));
        columnaDiferenciaPeso.setCellValueFactory(new PropertyValueFactory<>("diferenciaPeso"));
        columnaAlta.setCellValueFactory(new PropertyValueFactory<>("alta"));

        columnaId.setMinWidth(200);
        columnaFecha.setMinWidth(150);
        columnaCosecha.setMinWidth(200);
        columnaPesoSecadero.setMinWidth(200);
        columnaPesoCampo.setMinWidth(200);
        columnaDiferenciaPeso.setMinWidth(200);
        columnaAlta.setMinWidth(100);

        contenedor.setAlignment(Pos.CENTER);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorCarga.setAlignment(Pos.CENTER);
        contenedorHorizontal.setAlignment(Pos.CENTER);
        
        contenedorBotones.setPadding(new Insets(30, 10, 30, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal.setPadding(new Insets(10, 0, 30, 0));
        
        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);

        cosechasBox.setMinWidth(140);
        
        entradaPesoSecadero.setPromptText("Ingrese peso en Secadero (kgs)   ");
        entradaPesoSecadero.setMaxWidth(300);

        etiquetaFecha.setText("Ingrese Fecha:   ");
        etiquetaInteractiva2.setFont(fuenteNegrita);

        separador1H.setPadding(new Insets(0, 0, 30, 0));
        separador1V.setPadding(new Insets(0, 20, 0, 20));

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPadding(new Insets(0, 0, 10, 0));
        tabla.setPrefHeight(400);



    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarEntrega());
        botonEliminar.setOnAction(e -> clicEliminarEntrega());
        botonLimpiar.setOnAction(e -> limpiar());
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
        //empleadoBox.setOnAction(e -> cambiarEtiquetaEmpleado());

        //- cargamos datos a la tabla y los comboBoxs, a partir de consultas a la BD 
        tabla.getItems().addAll(this.servicio.listarEntregaSecadero());

        cosechasBox.getItems().addAll(servicio_Cosechas.listarCosechas());


        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaFecha);
        tabla.getColumns().add(columnaCosecha);
        tabla.getColumns().add(columnaPesoSecadero);
        tabla.getColumns().add(columnaPesoCampo);
        tabla.getColumns().add(columnaDiferenciaPeso);
        tabla.getColumns().add(columnaAlta);

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva, separador1H);
        contenedorHorizontal.getChildren().addAll(etiquetaFecha, datepicker, separador1V , etiquetaComboBox_cosechas, cosechasBox);
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorHorizontal, entradaPesoSecadero , contenedorBotones, etiquetaInteractiva2);
    
        return contenedor;     
       }


    
    // metodos de la VISTA 

    private boolean clicAgregarEntrega() {

        entregaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        try {

            //- Si no hay elemento seleccionado en la tabla, se tiene un nuevo objeto por agregar
            if (entregaSeleccionada == null) {
                
                servicio.agregarEntregaSecadero(cosechasBox.getValue(), datepicker.getValue(), 
                 Double.parseDouble(entradaPesoSecadero.getText()));


            //- SINO, si el productorSeleccionado está de Alta, podremos modificarlo a partir de su id
            } else {

                if (entregaSeleccionada.isAlta() == true) {
                    servicio.modificarEntregaSecadero(entregaSeleccionada.getIdEntrega(), cosechasBox.getValue(),  
                     Double.parseDouble(entradaPesoSecadero.getText()), datepicker.getValue());

            //- SINO, se informa y se retorna falso                  
                } else {

                    limpiar();
                    etiquetaInteractiva2.setText("La cosecha seleccionada está dada de BAJA. No se puede modificar.\n"); 
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

    // A partir de la seleccion de un objeto sobre la tabla, se cargan sus datos en los elementos de la pantalla    
        entregaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (entregaSeleccionada != null) {

            etiquetaInteractiva.setText("Está seleccionada la Entrega a Secadero con id: " + entregaSeleccionada.getIdEntrega());

            cosechasBox.setValue(entregaSeleccionada.getCosecha());
            entradaPesoSecadero.setText(entregaSeleccionada.getPesoSecadero().toString());
        }
    }


 
    private void clicEliminarEntrega() {

        entregaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (entregaSeleccionada != null) {

            servicio.eliminarEntregaSecadero(entregaSeleccionada.getIdEntrega());
            limpiar();
        }
    }


    
    private void limpiar() {

        //- limpiar elementos de la vista
        entradaPesoSecadero.setPromptText("Ingrese peso en Secadero (kgs)   ");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para modificarlas (si su estado de alta es Verdadero)");
        etiquetaInteractiva2.setText("");

        cosechasBox.getSelectionModel().clearSelection();
        entradaPesoSecadero.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarEntregaSecadero());
    } 


}
