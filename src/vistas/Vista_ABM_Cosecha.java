package vistas;
import modelo.Cosecha;
import modelo.Cuadro;
import modelo.Empleado;
import servicios.Servicio_Cosechas;
import servicios.Servicio_Cuadros;
import servicios.Servicio_Empleados;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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


public class Vista_ABM_Cosecha implements Vista {

    // el servicio con el que se va a comunicar la vista
    private final Servicio_Cosechas servicio;
    Servicio_Empleados servicio_Empleados;
    Servicio_Cuadros servicio_Cuadros;
    private Cosecha cosechaSeleccionada;
       
    // objetos de la pantalla
    TableView<Cosecha> tabla;
    TableColumn<Cosecha, Integer> columnaId;
    TableColumn<Cosecha, Empleado> columnaEmpleado;
    TableColumn<Cosecha, LocalDate> columnaFecha;
    TableColumn<Cosecha, ArrayList<Cuadro>> columnaCuadros;
    TableColumn<Cosecha, ArrayList<Double>> columnaKgsCosechados;
    Button botonAgregar, botonEliminar, botonSeleccionarCuadroAnterior, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;

    // objetos para tomar los datos
    ComboBox<Empleado> empleadoBox;
    ComboBox<Cuadro> cuadroBox;
    DatePicker datepicker;
    Label etiquetaEmpleado, etiquetaComboBox_empleado, etiquetaFecha, etiquetaCuadro, etiquetaKgsCosechados;
    TextField entradaKgsCosechados; 
    int ultimoCuadroSeleccionado ;

    public Vista_ABM_Cosecha(Servicio_Cosechas servicio, Servicio_Empleados servicio_Empleados, Servicio_Cuadros servicio_Cuadros){
        this.servicio = servicio;
        this.servicio_Empleados = servicio_Empleados;
        this.servicio_Cuadros = servicio_Cuadros;

    }

    @Override
    public Parent obtenerVista() {

    // definicion elementos de pantalla 

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
        HBox contenedorHorizontal1 = new HBox();
        HBox contenedorHorizontal2 = new HBox();
     
        Separator separador1 = new Separator(Orientation.VERTICAL);
        Separator separador2 = new Separator(Orientation.VERTICAL);
        Separator separador3 = new Separator(Orientation.HORIZONTAL);
        
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaComboBox_empleado = new Label ("Seleccione el empleado que ha realizado la cosecha   ");
        etiquetaEmpleado = new Label ("");
        etiquetaFecha = new Label ("Ingrese Fecha   ");
        etiquetaCuadro = new Label ("Cuadro:   ");
        etiquetaKgsCosechados = new Label("Kilos Cosechados:   ");

        datepicker = new DatePicker();

        entradaKgsCosechados = new TextField();

        empleadoBox = new ComboBox<>();
        cuadroBox = new ComboBox<>();
        tabla = new TableView<>(); 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");
        botonSeleccionarCuadroAnterior = new Button ("Seleccionar Cuadro anterior de cosechaSeleccionada");

        columnaId = new TableColumn<>("Id");
        columnaEmpleado = new TableColumn<>("Empleado");
        columnaFecha = new TableColumn<>("Fecha");
        columnaCuadros = new TableColumn<>("Cuadros Cosechados");
        columnaKgsCosechados = new TableColumn<>("Kgs Cosechados");
       

    // propiedades de elementos

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPrefHeight(300);

        separador1.setPrefHeight(100);
        separador2.setPrefHeight(100);
        separador3.setPrefWidth(100);
        separador1.setPadding(new Insets(10, 10, 10, 10));
        separador2.setPadding(new Insets(10, 10, 10, 197));

        
        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal1.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal2.setPadding(new Insets(10, 10, 10, 10));

        //- propiedades de COLUMNAS
        columnaId.setMinWidth(100);
        columnaFecha.setMinWidth(150);
        columnaEmpleado.setMinWidth(300);
        columnaCuadros.setMinWidth(300);
        columnaKgsCosechados.setMinWidth(300);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Cuadro"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        columnaCuadros.setCellValueFactory(new PropertyValueFactory<>("cuadros"));
        columnaKgsCosechados.setCellValueFactory(new PropertyValueFactory<>("kgsCosechados"));

   

    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarCosecha());
        botonEliminar.setOnAction(e -> clicEliminarCosecha());
        botonSeleccionarCuadroAnterior.setOnAction(e -> clicSeleccionarCuadroAnterior());
        botonLimpiar.setOnAction(e -> limpiar());
        empleadoBox.setOnAction(e -> cambiarEtiquetaEmpleado());

        empleadoBox.getItems().addAll(servicio_Empleados.listarEmpleados());
        cuadroBox.getItems().addAll(servicio_Cuadros.listarCuadros());
        tabla.getItems().addAll(this.servicio.listarCosechas());

        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaFecha);
        tabla.getColumns().add(columnaEmpleado);
        tabla.getColumns().add(columnaCuadros);
        tabla.getColumns().add(columnaKgsCosechados);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonSeleccionarCuadroAnterior, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll( contenedorBotones, etiquetaInteractiva , etiquetaId);
         
        contenedorHorizontal1.getChildren().addAll(etiquetaFecha, datepicker, separador1 , etiquetaComboBox_empleado, empleadoBox);
        
        contenedorHorizontal2.getChildren().addAll(etiquetaCuadro, cuadroBox, separador2,
         etiquetaKgsCosechados, entradaKgsCosechados);
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorHorizontal1, separador3, contenedorHorizontal2 );

        return contenedor;

    }



    private void clicAgregarCosecha() {
        // asumo selección simple
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        try {
            if (cosechaSeleccionada == null) {
                // Si no hay elemento seleccionado en la tabla, 
                // se tiene un nuevo objeto por agregar
                
                var cuadros = new ArrayList<Cuadro>(); 
                var kgsCosechados = new ArrayList<Double>(); 

                cuadros.add(cuadroBox.getValue());
                kgsCosechados.add(Double.parseDouble(entradaKgsCosechados.getText()));

                servicio.agregarCosecha(empleadoBox.getValue(), datepicker.getValue(),
                cuadros, kgsCosechados);

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
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (cosechaSeleccionada != null) {
            ultimoCuadroSeleccionado = cosechaSeleccionada.getCuadros().size()-1;
            etiquetaId.setText(String.valueOf(cosechaSeleccionada.getId_Cosecha()));
            datepicker.setValue(cosechaSeleccionada.getFecha());
            empleadoBox.setValue(cosechaSeleccionada.getEmpleado());
            cuadroBox.setValue(cosechaSeleccionada.getCuadros().get(cosechaSeleccionada.getCuadros().size()-1));
            entradaKgsCosechados.setText(cosechaSeleccionada.getkgsCosechados().get(cosechaSeleccionada.getkgsCosechados().size()-1).toString());
        } else {
            // algo? 
        }
    }


 
    private void clicEliminarCosecha() {
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (cosechaSeleccionada != null) {
            servicio.eliminarCosecha(cosechaSeleccionada.getId_Cosecha());
            limpiar();
        }
    }



    private void clicSeleccionarCuadroAnterior(){
        if (cosechaSeleccionada != null) {

            cuadroBox.setValue(cosechaSeleccionada.getCuadros().get(ultimoCuadroSeleccionado -1));
            entradaKgsCosechados.setText(cosechaSeleccionada.getkgsCosechados().get(ultimoCuadroSeleccionado -1).toString());
            ultimoCuadroSeleccionado -= 1; 
        } else {
            // algo? 
        }
    }




    /* private void clicEditarArray() {
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (cosechaSeleccionada != null) { 
            cuadroBox.setValue(cosechaSeleccionada.getCuadros().get(cosechaSeleccionada.getCuadros().size()-1));
            entradaKgsCosechados.setText(cosechaSeleccionada.getkgsCosechados().get(cosechaSeleccionada.getkgsCosechados().size()-1).toString());
        } else {
            // algo? 
        }
    } */

    private void limpiar() {
        etiquetaId.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        cuadroBox.getSelectionModel().clearSelection();;
        entradaKgsCosechados.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCosechas());
    } 


    private void cambiarEtiquetaEmpleado() {
        etiquetaEmpleado.setText("Esta cosecha fue realizada por el empleado \n" + empleadoBox.getValue().getId_Empleado());
    }

}
