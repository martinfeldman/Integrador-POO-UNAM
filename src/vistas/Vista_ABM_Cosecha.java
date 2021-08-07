package vistas;
import modelo.Cosecha;
import modelo.Cuadro;
import modelo.Empleado;
import servicios.Servicio_Cosechas;
import servicios.Servicio_Cuadros;
import servicios.Servicio_Empleados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.geometry.Insets;
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
    Button botonAgregar, botonEliminar, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;

    // objetos para tomar los datos
    ComboBox<Empleado> empleadoBox;
    DatePicker datepicker;
    Label etiquetaEmpleado, etiquetaComboBox_empleado, etiquetaFecha, etiquetaCuadro, etiquetaKgsCosechados;
    TextField entradaCuadro, entradaKgsCosechados; 


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
     
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaComboBox_empleado = new Label ("Seleccione el empleado que ha realizado la cosecha");
        etiquetaEmpleado = new Label ("");
        etiquetaFecha = new Label ("Fecha:");
        etiquetaCuadro = new Label ("Cuadro:");
        etiquetaKgsCosechados = new Label("Kilos Cosechados:");

        datepicker = new DatePicker();

        entradaCuadro = new TextField();
        entradaKgsCosechados = new TextField();

        empleadoBox = new ComboBox<>();
        tabla = new TableView<>(); 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id");
        columnaEmpleado = new TableColumn<>("Empleado");
        columnaFecha = new TableColumn<>("Fecha");
        columnaCuadros = new TableColumn<>("Cuadros Cosechados");
        columnaKgsCosechados = new TableColumn<>("Kgs Cosechados");
       

    // propiedades de elementos

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        etiquetaFecha.setText("Ingrese Fecha");

        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        
        //- propiedades de COLUMNAS
        columnaId.setMinWidth(100);
        columnaFecha.setMinWidth(150);
        columnaEmpleado.setMinWidth(200);
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
        botonLimpiar.setOnAction(e -> limpiar());
        empleadoBox.setOnAction(e -> cambiarEtiquetaEmpleado());

        empleadoBox.getItems().addAll(servicio_Empleados.listarEmpleados());
        tabla.getItems().addAll(this.servicio.listarCosechas());


        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaFecha);
        tabla.getColumns().add(columnaEmpleado);
        tabla.getColumns().add(columnaCuadros);
        tabla.getColumns().add(columnaKgsCosechados);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva , etiquetaId, 
         etiquetaFecha, datepicker, etiquetaComboBox_empleado, empleadoBox, etiquetaCuadro, entradaCuadro,
          etiquetaKgsCosechados, entradaKgsCosechados);
        contenedor.getChildren().addAll(tabla, contenedorCarga);

        return contenedor;

    }









    private void clicAgregarCosecha() {
        // asumo selección simple
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        try {
            if (cosechaSeleccionada == null) {
                // Si no hay elemento seleccionado en la tabla
                // Faltan completar los parametros
                servicio.agregarCosecha(empleadoBox.getValue(), datepicker.getValue());
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
            etiquetaId.setText(String.valueOf(cosechaSeleccionada.getId_Cosecha()));
            //entradaNombres.setText(cuadroSeleccionado.getNombres());
           // entradaApellidos.setText(cuadroSeleccionado.getApellidos());
           // departamentos.getSelectionModel().select(cuadroSeleccionado.getDepartamento());
        }
    }


 
    private void clicEliminarCosecha() {
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (cosechaSeleccionada != null) {
            servicio.eliminarCosecha(cosechaSeleccionada.getId_Cosecha());
            limpiar();
        }
    }

    
    private void limpiar() {
        etiquetaId.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        entradaCuadro.clear();
        entradaKgsCosechados.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCosechas());
    } 


    private void cambiarEtiquetaEmpleado() {
        etiquetaEmpleado.setText("Esta cosecha fue realizada por el empleado \n" + empleadoBox.getValue().getId_Empleado());
    }

}
