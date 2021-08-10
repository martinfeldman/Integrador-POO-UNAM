package vistas;
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
import modelo.Cuadro;
import modelo.Empleado;
import modelo.Lote;
import modelo.Productor;
import servicios.Servicio_Cuadros;
import servicios.Servicio_Empleados;
import servicios.Servicio_Lotes;
import servicios.Servicio_Productores;
import servicios.Servicio_seguimientoEmpleado;


// Para utilizar esta vista, el constructor requiere los dos servicios utilizados.
// Para esta vista se utilizó como referencia la vista de empleado del ejemploIntegrador

public class Vista_ABM_Empleado implements Vista {

    // los servicios con el que se va a comunicar la vista
    private final Servicio_Empleados servicio;
    Servicio_seguimientoEmpleado servicio_seguimiento;
    Servicio_Productores servicio_Productores;
    Servicio_Lotes servicio_Lotes;
    Servicio_Cuadros servicio_Cuadros;
    private Empleado empleadoSeleccionado;


    // objetos de la pantalla
    
    Button botonAgregar, botonEliminar, botonLimpiar;
    Label etiquetaInteractiva;
    TableView<Empleado> tabla;
    TableColumn<Empleado, Integer> columnaId;
    TableColumn<Empleado, String> columnaNombres;
    TableColumn<Empleado, String> columnaApellidos;
    TableColumn<Empleado, String> columnaDni;
    TextField entradaNombres, entradaApellidos, entradaDni;
  
    // objetos para seguimiento de empleados
    Button botonKgsPorProductor, botonKgsPorLote, botonKgsPorCuadro;
    ComboBox<Productor> productorBox;
    ComboBox<Lote> loteBox;
    ComboBox<Cuadro> cuadroBox;
    Label etiquetaKgsPorProductor, etiquetaKgsPorLote, etiquetaKgsPorCuadro;

    
    public Vista_ABM_Empleado(Servicio_Empleados servicio, Servicio_seguimientoEmpleado servicio_seguimiento,
     Servicio_Productores servicio_Productores, Servicio_Lotes servicio_Lotes, Servicio_Cuadros servicio_Cuadros) {
        this.servicio = servicio;
        this.servicio_seguimiento = servicio_seguimiento;
        this.servicio_Productores = servicio_Productores;
        this.servicio_Lotes = servicio_Lotes;
        this.servicio_Cuadros = servicio_Cuadros;
    }



    @Override
    public Parent obtenerVista() {
        

    // definicion elementos de pantalla
        
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaKgsPorProductor = new Label("");
        etiquetaKgsPorLote = new Label("");
        etiquetaKgsPorCuadro = new Label("");

        entradaNombres = new TextField();
        entradaApellidos = new TextField();
        entradaDni = new TextField();

        entradaNombres.setMaxWidth(400);
        entradaApellidos.setMaxWidth(400);
        entradaDni.setMaxWidth(300);

        botonAgregar = new Button("Agregar");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");
        botonKgsPorProductor = new Button("obtener kgs cosechados por X productor");
        botonKgsPorLote = new Button("obtener kgs cosechados por X lote");
        botonKgsPorCuadro = new Button("obtener kgs cosechados por X cuadro");

        productorBox = new ComboBox<>();
        loteBox = new ComboBox<>();
        cuadroBox = new ComboBox<>();
        
        columnaId = new TableColumn<>("Id de Empleado");
        columnaDni = new TableColumn<>("DNI");
        columnaNombres = new TableColumn<>("Nombres");
        columnaApellidos = new TableColumn<>("Apellidos");

        tabla = new TableView<>();

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();

        

    // propiedades de elementos

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPrefHeight(300);

        entradaNombres.setPromptText("Nombres del empleado");       
        entradaApellidos.setPromptText("Apellidos del empleado");
        entradaDni.setPromptText("DNI del empleado");

        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));

        //- COLUMNAS - propiedades
      
        columnaId.setMinWidth(200);
        columnaDni.setMinWidth(200);
        columnaNombres.setMinWidth(300);
        columnaApellidos.setMinWidth(300);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnaNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        


    // acciones sobre elementos

        botonAgregar.setOnAction(e -> clicAgregarEmpleado());
        botonEliminar.setOnAction(e -> clicEliminarEmpleado());
        botonLimpiar.setOnAction(e -> limpiar());
        productorBox.setOnAction(e -> servicio_seguimiento.obtenerKgsEmpleado_productor());
        loteBox.setOnAction(e -> servicio_seguimiento.obtenerKgsEmpleado_lote());
        cuadroBox.setOnAction(e -> servicio_seguimiento.obtenerKgsEmpleado_cuadro());
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());   

        //- cargamos datos a la tabla y los comboBoxs, a partir de consultas a la BD 
        tabla.getItems().addAll(this.servicio.listarEmpleados());

        productorBox.getItems().addAll(this.servicio_Productores.listarProductores());
        loteBox.getItems().addAll(this.servicio_Lotes.listarLotes());
        cuadroBox.getItems().addAll(this.servicio_Cuadros.listarCuadros());

        //- agregamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaDni);
        tabla.getColumns().add(columnaNombres);
        tabla.getColumns().add(columnaApellidos);
       
        
        //- agregamos contenido a los contenedores  
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(etiquetaInteractiva, entradaNombres, entradaApellidos, entradaDni);
        contenedor.getChildren().addAll(tabla, contenedorCarga, contenedorBotones);

        return contenedor;

    }


    // como mostrar las cosechas de un empleado? 
    // Podria crear otra tabla debajo que lea el empleadoSeleccionado 
    // en la tabla de arriba y haga la busqueda. 




    private void clicAgregarEmpleado() {
        
        empleadoSeleccionado = tabla.getSelectionModel().getSelectedItem();

        try {

            // Si no hay elemento seleccionado en la tabla, se tiene un nuevo objeto por agregar
            if (empleadoSeleccionado == null) {
                servicio.agregarEmpleado(entradaNombres.getText(), entradaApellidos.getText(), entradaDni.getText());
           

            //- SINO, modificamos el empleadoSeleccionado a partir de su id
            } else {
                servicio.modificarEmpleado(empleadoSeleccionado.getIdEmpleado(), entradaNombres.getText(), entradaApellidos.getText(), entradaDni.getText());
            }

            limpiar();

        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }



    private void cargarDatos() {

        //- A partir de la seleccion de un objeto sobre la tabla, Se cargan sus datos en los elementos de la pantalla
        empleadoSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado != null) {

            etiquetaInteractiva.setText("Está seleccionado el Empleado con id: " + empleadoSeleccionado.getIdEmpleado());
            
            entradaNombres.setText(empleadoSeleccionado.getNombres());
            entradaApellidos.setText(empleadoSeleccionado.getApellidos());
            entradaDni.setText(empleadoSeleccionado.getDni());
        }
    }

    

    private void clicEliminarEmpleado() {

        empleadoSeleccionado = tabla.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado != null) {

            servicio.eliminarEmpleado(empleadoSeleccionado.getIdEmpleado());
            limpiar();
        }
    }


    private void limpiar() {
        
        //- limpiar elementos de la vista 
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        
        entradaNombres.clear();
        entradaApellidos.clear();
        entradaDni.clear();

        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarEmpleados());
    } 


}
