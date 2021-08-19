package vistas;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    Label etiquetaInteractiva, etiquetaComboBox_cosechas, etiquetaFecha, etiquetaPesoSecadero;
    TableView<EntregaSecadero> tabla;
    TableColumn<EntregaSecadero, Integer> columnaId;
    TableColumn<EntregaSecadero, LocalDate> columnaFecha;
    TableColumn<EntregaSecadero, ArrayList<Cosecha>> columnaCosecha;
    TableColumn<EntregaSecadero, Double> columnaPesoSecadero;
    TableColumn<EntregaSecadero, Double> columnaPesoCampo;
    TableColumn<EntregaSecadero, Double> columnaDiferenciaPeso;
    TableColumn<EntregaSecadero, Boolean> columnaAlta;
    TextField entradaNombres, entradaApellidos, entradaDni, entradaPesoSecadero; 


    public Vista_ABM_EntregaSecadero(Servicio_EntregasSecadero servicio, Servicio_Cosechas servicio_Cosechas){
        this.servicio = servicio;
        this.servicio_Cosechas = servicio_Cosechas;
    }



    @Override
    public Parent obtenerVista() {

    // definicion elementos de pantalla 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
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

        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaComboBox_cosechas = new Label ("Seleccione la cosecha que llega en la entrega   ");
        etiquetaFecha = new Label ("Fecha de Entrega:   ");
        etiquetaPesoSecadero = new Label ("Peso (en kgs) en Secadero:   ");    

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
        
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal.setPadding(new Insets(10, 0, 10, 0));
        
        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);
       
        etiquetaFecha.setText("Ingrese Fecha:   ");
        entradaPesoSecadero.setMaxWidth(300);

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPadding(new Insets(0, 0, 10, 0));
        tabla.setPrefHeight(300);



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
        contenedorCarga.getChildren().addAll(etiquetaInteractiva , etiquetaFecha, datepicker);
        contenedorHorizontal.getChildren().addAll(etiquetaComboBox_cosechas, cosechasBox);
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorHorizontal, etiquetaPesoSecadero, entradaPesoSecadero , contenedorBotones);
    
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

                    System.out.print("La cosecha seleccionada está dada de BAJA. No se puede modificar.\n"); 
                    limpiar();
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
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        cosechasBox.getSelectionModel().clearSelection();
        entradaPesoSecadero.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarEntregaSecadero());
    } 


}
