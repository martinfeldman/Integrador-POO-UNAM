package vistas;
import java.time.LocalDate;

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
import modelo.Cosecha;
import modelo.Cuadro;
import modelo.Empleado;
import servicios.Servicio_Cosechas;
import servicios.Servicio_Cuadros;
import servicios.Servicio_Empleados;


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
    TableColumn<Cosecha, Cuadro> columnaCuadros;
    TableColumn<Cosecha, Double> columnaKgsCosechados;
    Button botonAgregar, botonEliminar, botonLimpiar;
    Label etiquetaInteractiva;
    Separator separador1, separador2, separador3;

    // objetos para tomar los datos
    ComboBox<Empleado> empleadoBox;
    ComboBox<Cuadro> cuadroBox;
    DatePicker datepicker;
    Label etiquetaEmpleado, etiquetaComboBox_empleado, etiquetaFecha, etiquetaCuadro, etiquetaKgsCosechados;
    TextField entradaKgsCosechados; 


    public Vista_ABM_Cosecha(Servicio_Cosechas servicio, Servicio_Empleados servicio_Empleados, Servicio_Cuadros servicio_Cuadros){
        this.servicio = servicio;
        this.servicio_Empleados = servicio_Empleados;
        this.servicio_Cuadros = servicio_Cuadros;

    }

    @Override
    public Parent obtenerVista() {

    // definicion elementos de pantalla 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id Cosecha");
        columnaEmpleado = new TableColumn<>("Empleado");
        columnaFecha = new TableColumn<>("Fecha");
        columnaCuadros = new TableColumn<>("Cuadro Cosechado");
        columnaKgsCosechados = new TableColumn<>("Kgs Cosechados");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
        HBox contenedorHorizontal1 = new HBox();
        HBox contenedorHorizontal2 = new HBox();

        cuadroBox = new ComboBox<>();

        datepicker = new DatePicker();

        empleadoBox = new ComboBox<>();
        
        entradaKgsCosechados = new TextField();
        
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaComboBox_empleado = new Label ("Seleccione el empleado que ha realizado la cosecha   ");
        etiquetaEmpleado = new Label ("");
        etiquetaFecha = new Label ("Ingrese Fecha   ");
        etiquetaCuadro = new Label ("Cuadro:   ");
        etiquetaKgsCosechados = new Label("Kilos Cosechados:   ");
      
        separador1 = new Separator(Orientation.VERTICAL);
        separador2 = new Separator(Orientation.VERTICAL);
        separador3 = new Separator(Orientation.HORIZONTAL);
        

        tabla = new TableView<>();    
       
       

    // propiedades de elementos

        //- propiedades de COLUMNAS
        columnaId.setMinWidth(100);
        columnaFecha.setMinWidth(150);
        columnaEmpleado.setMinWidth(300);
        columnaCuadros.setMinWidth(200);
        columnaKgsCosechados.setMinWidth(200);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("idCosecha"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        columnaCuadros.setCellValueFactory(new PropertyValueFactory<>("cuadro"));
        columnaKgsCosechados.setCellValueFactory(new PropertyValueFactory<>("kgsCosechados"));

        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal1.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal2.setPadding(new Insets(10, 10, 10, 10));

        separador1.setPrefHeight(100);
        separador2.setPrefHeight(100);
        separador3.setPrefWidth(100);
        separador1.setPadding(new Insets(10, 10, 10, 10));
        separador2.setPadding(new Insets(10, 10, 10, 140));

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPrefHeight(300);
        tabla.setPrefWidth(950);

 

    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarCosecha());
        botonEliminar.setOnAction(e -> clicEliminarCosecha());
        botonLimpiar.setOnAction(e -> limpiar());
        empleadoBox.setOnAction(e -> cambiarEtiquetaEmpleado());
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        empleadoBox.getItems().addAll(servicio_Empleados.listarEmpleados());
        cuadroBox.getItems().addAll(servicio_Cuadros.listarCuadros());
        tabla.getItems().addAll(this.servicio.listarCosechas());

        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaFecha);
        tabla.getColumns().add(columnaEmpleado);
        tabla.getColumns().add(columnaCuadros);
        tabla.getColumns().add(columnaKgsCosechados);
    

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva);
         
        contenedorHorizontal1.getChildren().addAll(etiquetaFecha, datepicker, separador1 , etiquetaComboBox_empleado, empleadoBox); 
        contenedorHorizontal2.getChildren().addAll(etiquetaCuadro, cuadroBox, separador2,
         etiquetaKgsCosechados, entradaKgsCosechados);

        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorHorizontal1, separador3,
         contenedorHorizontal2, contenedorBotones);

        return contenedor;

    }



    private void clicAgregarCosecha() {

        //- A partir del objetoSeleccionado en la tabla
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        try {

            if (cosechaSeleccionada == null) {
        //- Si no hay elemento seleccionado en la tabla,se tiene un nuevo objeto por agregar

                servicio.agregarCosecha(empleadoBox.getValue(), datepicker.getValue(),
                cuadroBox.getValue(), Double.parseDouble(entradaKgsCosechados.getText()));

        //- SINO, modificamos la cosechaSeleccionada a partir de su id
            } else {

                servicio.modificarCosecha(cosechaSeleccionada.getIdCosecha(), empleadoBox.getValue(),
                 datepicker.getValue(), cuadroBox.getValue(), 
                  Double.parseDouble(entradaKgsCosechados.getText())) ;
            }

            limpiar();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }



    private void cargarDatos() {
        
    // A partir de la seleccion de un objeto sobre la tabla, se cargan sus datos en los elementos de la pantalla
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (cosechaSeleccionada != null) {
          
            cuadroBox.setValue(cosechaSeleccionada.getCuadro());
            datepicker.setValue(cosechaSeleccionada.getFecha());
            empleadoBox.setValue(cosechaSeleccionada.getEmpleado());
            entradaKgsCosechados.setText(cosechaSeleccionada.getKgsCosechados().toString());

        } else {
            // algo? 
        }
    }


 
    private void clicEliminarCosecha() {

        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (cosechaSeleccionada != null) {
            servicio.eliminarCosecha(cosechaSeleccionada.getIdCosecha());
            limpiar();
        }
    }



    private void limpiar() {
        
        //- limpiar elementos de la vista 
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas" + cosechaSeleccionada.getIdCosecha());
        empleadoBox.getSelectionModel().clearSelection();
        cuadroBox.getSelectionModel().clearSelection();
        entradaKgsCosechados.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCosechas());
    } 



    private void cambiarEtiquetaEmpleado() {
        if (empleadoBox.getValue() != null){
            etiquetaEmpleado.setText("Esta cosecha fue realizada por el empleado \n" + empleadoBox.getValue().getIdEmpleado());
        }
    }

}
