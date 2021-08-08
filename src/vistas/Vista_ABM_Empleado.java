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
    TableView<Empleado> tabla;
    TableColumn<Empleado, Integer> columnaId;
    TableColumn<Empleado, String> columnaNombres;
    TableColumn<Empleado, String> columnaApellidos;
    TableColumn<Empleado, String> columnaDni;
    Button botonAgregar, botonEliminar, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;

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
        
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");
        etiquetaKgsPorProductor = new Label("");
        etiquetaKgsPorLote = new Label("");
        etiquetaKgsPorCuadro = new Label("");

        entradaNombres = new TextField();
        entradaApellidos = new TextField();
        entradaDni = new TextField();

        botonAgregar = new Button("Agregar");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");
        botonKgsPorProductor = new Button("obtener kgs cosechados por X productor");
        botonKgsPorLote = new Button("obtener kgs cosechados por X lote");
        botonKgsPorCuadro = new Button("obtener kgs cosechados por X cuadro");

        productorBox = new ComboBox<>();
        loteBox = new ComboBox<>();
        cuadroBox = new ComboBox<>();
        
        columnaId = new TableColumn<>("Id");
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

        // COLUMNAS - propiedades
      
        columnaId.setMinWidth(100);
        columnaDni.setMinWidth(200);
        columnaNombres.setMinWidth(300);
        columnaApellidos.setMinWidth(300);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Empleado"));
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

        // pedimos los datos de Empleados a la BD y mostramos en tabla 
        tabla.getItems().addAll(this.servicio.listarEmpleados());
        // cargamos datos en los comboBoxs
        productorBox.getItems().addAll(this.servicio_Productores.listarProductores());
        loteBox.getItems().addAll(this.servicio_Lotes.listarLotes());
        cuadroBox.getItems().addAll(this.servicio_Cuadros.listarCuadros());

        // agregamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaDni);
        tabla.getColumns().add(columnaNombres);
        tabla.getColumns().add(columnaApellidos);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());   
        
        // agregamos contenido a los contenedores  
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva, etiquetaId, entradaNombres, entradaApellidos, entradaDni);
        contenedor.getChildren().addAll(tabla, contenedorCarga);

        return contenedor;

    }


    // como mostrar las cosechas de un empleado? 
    // Podria crear otra tabla debajo que lea el empleadoSeleccionado 
    // en la tabla de arriba y haga la busqueda. 




    private void clicAgregarEmpleado() {
        
        empleadoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        try {
            if (empleadoSeleccionado == null) {
                // Si no hay elemento seleccionado en la tabla
                servicio.agregarEmpleado(entradaNombres.getText(), entradaApellidos.getText(), entradaDni.getText());
            } else {
                // SINO modificar el empleado
                servicio.editarEmpleado(Integer.parseInt(etiquetaId.getText()), entradaNombres.getText(), entradaApellidos.getText(), entradaDni.getText());
            }
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }



    // A partir de la seleccion de un objeto sobre la tabla, Se cargan sus datos en los elementos de la pantalla
    private void cargarDatos() {
        empleadoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            etiquetaInteractiva.setText("Está seleccionado el Empleado con id: ");
            etiquetaId.setText(String.valueOf(empleadoSeleccionado.getId_Empleado()));
            entradaNombres.setText(empleadoSeleccionado.getNombres());
            entradaApellidos.setText(empleadoSeleccionado.getApellidos());
           
            // este se usaria en Vista_ABM_Cuadro, y Vista_ABM_Lote
            //departamentos.getSelectionModel().select(empleadoSeleccionado.getDepartamento());
        }
    }

    

    private void clicEliminarEmpleado() {
        empleadoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            servicio.eliminarEmpleado(empleadoSeleccionado.getId_Empleado());
            limpiar();
        }
    }


    // limpiar vista 
    private void limpiar() {
    
        etiquetaId.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas");
        
        entradaNombres.clear();
        entradaApellidos.clear();
        entradaDni.clear();
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarEmpleados());
    } 


}
