package vistas;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Empleado;
import servicios.Servicio_Empleados;
import servicios.Servicio_seguimientoEmpleado;


// Para utilizar esta vista, el constructor requiere los dos servicios utilizados.
// Para esta vista se utilizó como referencia la vista de empleado del ejemploIntegrador

public class Vista_ABM_Empleado implements Vista {

    // los servicios con el que se va a comunicar la vista
    private final Servicio_Empleados servicio;
    Servicio_seguimientoEmpleado servicio_seguimiento;
    private Empleado empleadoSeleccionado;


    // objetos de la pantalla
    TableView<Empleado> tabla;
    TableColumn<Empleado, Integer> columnaId;
    TableColumn<Empleado, String> columnaNombres;
    TableColumn<Empleado, String> columnaApellidos;
    TableColumn<Empleado, String> dni;
    Button botonAgregar, botonEliminar, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;

    
    public Vista_ABM_Empleado(Servicio_Empleados servicio, Servicio_seguimientoEmpleado servicio_seguimiento) {
        this.servicio = servicio;
        this.servicio_seguimiento = servicio_seguimiento;
    }



    @Override
    public Parent obtenerVista() {
        
        // creamos elementos  de pantalla 
        // labels & textFields
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");

        entradaNombres = new TextField();
        entradaApellidos = new TextField();
        entradaDni = new TextField();

        entradaNombres.setPromptText("Nombres del empleado");       
        entradaApellidos.setPromptText("Apellidos del empleado");
        entradaDni.setPromptText("DNI del empleado");



        //  tres botones principales y asociamos eventos
        botonAgregar = new Button("Agregar");
        botonAgregar.setOnAction(e -> clicAgregarEmpleado());

        botonEliminar = new Button("Eliminar");
        botonEliminar.setOnAction(e -> clicEliminarEmpleado());

        botonLimpiar = new Button("Limpiar");
        botonLimpiar.setOnAction(e -> limpiar());



        // contenedor para los botones
        HBox contenedorBotones = new HBox();
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorBotones.setSpacing(10);


        // agregamos botones al contenedor  
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);



        // contenedor inferior para la carga (inputs y botones y mensajes)
        VBox contenedorCarga = new VBox();
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setSpacing(10);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva, etiquetaId, entradaNombres, entradaApellidos, entradaDni);



        // COLUMNAS y sus propiedades
        var columnaIdEmpleado = new TableColumn<>("Id");
        columnaIdEmpleado.setMinWidth(100);
        columnaIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        
        var columnaDni = new TableColumn<>("DNI");
        columnaDni.setMinWidth(100);
        columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        var columnaNombres = new TableColumn<>("Nombres");
        columnaNombres.setMinWidth(300);
        columnaNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
       
        var columnaApellidos = new TableColumn<>("Apellidos");
        columnaApellidos.setMinWidth(300);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        
        // como mostrar las cosechas de un empleado? 
        // Podria crear otra tabla debajo que lea el empleadoSeleccionado 
        // en la tabla de arriba y haga la busqueda. 


        // TABLA
        var tabla = new TableView<>();
        // opcional (como es la seleccion: unica, multiple, etc)
        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);




        // pedimos los datos de Empleados a la BD y mostramos en tabla 
        tabla.getItems().addAll(this.servicio.listarEmpleados());


        // agregamos las columnas a la tabla
        tabla.getColumns().add(columnaIdEmpleado);
        tabla.getColumns().add(columnaDni);
        tabla.getColumns().add(columnaNombres);
        tabla.getColumns().add(columnaApellidos);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
        
        
        // Contenedor pincipal:  para la tabla y contenedor de carga
        VBox contenedor = new VBox();
        // agregamos al contenedor la tabla
        contenedor.getChildren().addAll(tabla, contenedorCarga);

        return contenedor;

    }



    

    private void clicAgregarEmpleado() {
        // asumo selección simple
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
        var tabla = new TableView<>();

        // quitamos la selección en la tabla
        tabla.getSelectionModel().clearSelection();

        etiquetaId.setText("");
        entradaNombres.clear();
        entradaApellidos.clear();
        entradaDni.clear();
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarEmpleados());
    } 
}
