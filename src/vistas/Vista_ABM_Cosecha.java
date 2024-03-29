package vistas;
import java.time.LocalDate;

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
    Font fuenteNegrita;
    TableView<Cosecha> tabla;
    TableColumn<Cosecha, Integer> columnaId;
    TableColumn<Cosecha, Empleado> columnaEmpleado;
    TableColumn<Cosecha, LocalDate> columnaFecha;
    TableColumn<Cosecha, Cuadro> columnaCuadros;
    TableColumn<Cosecha, Double> columnaKgsCosechados;
    TableColumn<Cosecha, Boolean> columnaAlta;
    Button botonAgregar, botonEliminar, botonLimpiar;
    Label etiquetaInteractiva, etiquetaInteractiva2, etiquetaInformativa;
    Separator separador1H, separador2H, separador1V, separador2V;

    // objetos para tomar los datos
    ComboBox<Empleado> empleadoBox;
    ComboBox<Cuadro> cuadroBox;
    DatePicker datepicker;
    Label etiquetaComboBox_empleado, etiquetaFecha, etiquetaCuadro, etiquetaKgsCosechados;
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
        botonEliminar = new Button("Dar de baja");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id de Cosecha");
        columnaEmpleado = new TableColumn<>("Empleado");
        columnaFecha = new TableColumn<>("Fecha");
        columnaCuadros = new TableColumn<>("Cuadro Cosechado");
        columnaKgsCosechados = new TableColumn<>("Kgs Cosechados");
        columnaAlta = new TableColumn<>("Alta");

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
        HBox contenedorHorizontal1 = new HBox();
        HBox contenedorHorizontal2 = new HBox();
        VBox contenedor2V = new VBox();
        HBox contenedor3H = new HBox();


        cuadroBox = new ComboBox<>();

        datepicker = new DatePicker();

        empleadoBox = new ComboBox<>();
        
        entradaKgsCosechados = new TextField();

        etiquetaComboBox_empleado = new Label ("Seleccione el empleado que ha realizado la cosecha   ");
        etiquetaCuadro = new Label ("Cuadro:   ");
        etiquetaFecha = new Label ("Ingrese Fecha   ");
        etiquetaInformativa = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para modificarlas (si su estado de alta es Verdadero)");
        etiquetaInteractiva2 = new Label("");
        etiquetaKgsCosechados = new Label("Kilos Cosechados:   ");

        fuenteNegrita = Font.font("", FontWeight.BOLD, FontPosture.REGULAR , 15);
      
        separador1V = new Separator(Orientation.VERTICAL);
        separador2V = new Separator(Orientation.VERTICAL);
        separador1H = new Separator(Orientation.HORIZONTAL);
        separador2H = new Separator(Orientation.HORIZONTAL);

        tabla = new TableView<>();    
       
       

    // propiedades de elementos

        //- propiedades de COLUMNAS
        columnaId.setCellValueFactory(new PropertyValueFactory<>("idCosecha"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleado"));
        columnaCuadros.setCellValueFactory(new PropertyValueFactory<>("cuadro"));
        columnaKgsCosechados.setCellValueFactory(new PropertyValueFactory<>("kgsCosechados"));
        columnaAlta.setCellValueFactory(new PropertyValueFactory<>("alta"));

        columnaId.setMinWidth(200);
        columnaFecha.setMinWidth(150);
        columnaEmpleado.setMinWidth(300);
        columnaCuadros.setMinWidth(200);
        columnaKgsCosechados.setMinWidth(200);
        columnaAlta.setMinWidth(100);

        contenedor.setAlignment(Pos.CENTER);
        contenedorBotones.setAlignment(Pos.CENTER);
        contenedorCarga.setAlignment(Pos.CENTER);
        contenedorHorizontal1.setAlignment(Pos.CENTER);
        contenedorHorizontal2.setAlignment(Pos.CENTER);
        contenedor2V.setAlignment(Pos.CENTER);
        contenedor3H.setAlignment(Pos.CENTER);

        contenedorBotones.setPadding(new Insets(10, 10, 30, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal1.setPadding(new Insets(10, 10, 10, 10));
        contenedorHorizontal2.setPadding(new Insets(10, 10, 10, 10));

        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);

        contenedor2V.setMinWidth(300);

        cuadroBox.setMinWidth(120);
        empleadoBox.setMinWidth(200);

        etiquetaInformativa.setPadding(new Insets(10, 0, 0, 0));
        etiquetaInteractiva2.setFont(fuenteNegrita);
        
        separador1H.setPadding(new Insets(0, 0, 30, 0));
        separador1V.setPadding(new Insets(0, 15, 10, 15));
        separador2V.setPadding(new Insets(10, 20, 10, 70));

        separador1V.setPrefHeight(100);
        separador2V.setPrefHeight(100);

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPrefHeight(400);
        tabla.setPrefWidth(1150);



    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarCosecha());
        botonEliminar.setOnAction(e -> clicEliminarCosecha());
        botonLimpiar.setOnAction(e -> limpiar());
        empleadoBox.setOnAction(e -> cambiarEtiquetaComboBox_Empleado());
        cuadroBox.setOnAction(e -> cambiarEtiquetaInformativa());
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
        tabla.getColumns().add(columnaAlta);
    

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva, separador1H);
         
        contenedorHorizontal1.getChildren().addAll(etiquetaFecha, datepicker, separador1V , etiquetaComboBox_empleado, empleadoBox); 
        contenedor3H.getChildren().addAll(etiquetaCuadro, cuadroBox);
        contenedor2V.getChildren().addAll(contenedor3H, etiquetaInformativa);
        contenedorHorizontal2.getChildren().addAll(contenedor2V, separador2V, etiquetaKgsCosechados, entradaKgsCosechados);

        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorHorizontal1, separador2H,
         contenedorHorizontal2, contenedorBotones, etiquetaInteractiva2);

        return contenedor;

    }



    private boolean clicAgregarCosecha() {

        //- A partir del objetoSeleccionado en la tabla
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        try {

            if (cosechaSeleccionada == null) {
        //- Si no hay elemento seleccionado en la tabla,se tiene un nuevo objeto por agregar

                servicio.agregarCosecha(empleadoBox.getValue(), datepicker.getValue(),
                cuadroBox.getValue(), Double.parseDouble(entradaKgsCosechados.getText()));

        //- SINO, si el productorSeleccionado está de Alta, podremos modificarlo a partir de su id
            } else {

                if (cosechaSeleccionada.isAlta() == true) {
                    servicio.modificarCosecha(cosechaSeleccionada.getIdCosecha(), empleadoBox.getValue(),
                     datepicker.getValue(), cuadroBox.getValue(), 
                      Double.parseDouble(entradaKgsCosechados.getText())) ;

                
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
        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (cosechaSeleccionada != null) {
          
            etiquetaInteractiva.setText("Está seleccionada la Cosecha con id: " + String.valueOf(cosechaSeleccionada.getIdCosecha()));
            etiquetaComboBox_empleado.setPadding(new Insets(0, 15, 0, 0));

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



    private void cambiarEtiquetaComboBox_Empleado() {

        cosechaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (empleadoBox.getValue() != null && cosechaSeleccionada != null ){

            etiquetaComboBox_empleado.setText("Esta cosecha fue realizada por el empleado " + empleadoBox.getValue().getIdEmpleado());
        }
    }



    private void cambiarEtiquetaInformativa() {

        //- luego de seleccionar algo en cuadroBox, a su derecha esta etiqueta informa a qué lote y qué productor pertence el cuadro
        if (cuadroBox.getValue() != null){

            etiquetaInformativa.setText("Este cuadro pertenece al productor " + cuadroBox.getValue().getLote().getProductor().toString() + " y al " + cuadroBox.getValue().getLote().toString() + "\n");
            
        } else {

        }
    }


    private void limpiar() {
        
        //- limpiar elementos de la vista   
        etiquetaInformativa.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para modificarlas (si su estado de alta es Verdadero)");
        etiquetaInteractiva2.setText("");
        
        etiquetaComboBox_empleado.setText("Seleccione el empleado que ha realizado la cosecha   ");
        etiquetaComboBox_empleado.setPadding(new Insets(0, 0, 0, 0));
        
        entradaKgsCosechados.clear();

        cuadroBox.getSelectionModel().clearSelection();
        empleadoBox.getSelectionModel().clearSelection();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarCosechas());
    } 

}
