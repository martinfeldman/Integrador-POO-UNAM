package vistas;
import modelo.Empleado;
import modelo.Productor;
import servicios.Servicio_Empleados;
import servicios.Servicio_Productores;

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

// para esta vista se utilizó como referencia la vista de empleado del ejemploIntegrador

public class Vista_ABM_Productor implements Vista {

    // el servicio con el que se va a comunicar la vista
    private final Servicio_Productores servicio;
    private Productor productorSeleccionado;

    // objetos de la pantalla
    TableView<Productor> tabla;
    TableColumn<Productor, Integer> columnaId;
    TableColumn<Productor, String> columnaNombres;
    TableColumn<Productor, String> columnaApellidos;
    TableColumn<Productor, String> columnaDni;
    Button botonAgregar, botonEliminar, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;


    public Vista_ABM_Productor(Servicio_Productores servicio) {
        this.servicio = servicio;
    }


    @Override
    public Parent obtenerVista() {
        
        //  creamos elementos  de pantalla 
        //- labels & textfields
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");

        entradaNombres = new TextField();
        entradaApellidos = new TextField();
        entradaDni = new TextField();
        
        entradaNombres.setPromptText("Nombres del productor");
        entradaApellidos.setPromptText("Apellidos del productor");
        entradaDni.setPromptText("DNI del productor");
    


        // tres botones principales y asociamos eventos
        botonAgregar = new Button("Agregar/Modificar Selección");
        botonAgregar.setOnAction(e -> clicAgregarProductor());

        botonEliminar = new Button("Eliminar");
        botonEliminar.setOnAction(e -> clicEliminarProductor());

        botonLimpiar = new Button("Limpiar");
        botonLimpiar.setOnAction(e -> limpiar());



        //  contenedor para los botones 
        HBox contenedorBotones = new HBox();
        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorBotones.setSpacing(10);


        // agregamos  botones al contenedorBotones
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);



        // contenedor inferior para la carga (inputs y botones y mensajes)
        VBox contenedorCarga = new VBox();
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setSpacing(10);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva , etiquetaId, entradaNombres, entradaApellidos, entradaDni);


        // TABLA 
        tabla = new TableView<>();

        // TABLA: definimos las columnas y sus propiedades
        columnaId = new TableColumn<>("Id");
        columnaId.setMinWidth(100);
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Productor"));

        columnaDni = new TableColumn<>("DNI");
        columnaDni.setMinWidth(100);
        columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        columnaNombres = new TableColumn<>("Nombres");
        columnaNombres.setMinWidth(300);
        columnaNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));

        columnaApellidos = new TableColumn<>("Apellidos");
        columnaApellidos.setMinWidth(300);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        // como mostrar los lotes y cuadros de un productor? 
        // Podria crear otra tabla debajo que lea el productorSeleccionado 
        // en la tabla de arriba y haga la busqueda. 


      
        // opcional (como es la seleccion: unica, multiple, etc)
        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);



        // pedimos los datos de Productores a la BD y mostramos en tabla 
        tabla.getItems().addAll(this.servicio.listarProductores());


        // agregamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
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




 


    private void clicAgregarProductor(){
     // asumo selección simple
        productorSeleccionado = tabla.getSelectionModel().getSelectedItem();
        try {
            if (productorSeleccionado == null) {
                // Si no hay elemento seleccionado en la tabla
                servicio.agregarProductor(entradaNombres.getText(), entradaApellidos.getText(), entradaDni.getText());
            } else {
                // SINO modificar el productor
                servicio.editarProductor(Integer.parseInt(etiquetaId.getText()), entradaNombres.getText(), entradaApellidos.getText(), entradaDni.getText());
            }
            limpiar();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "Error al guardar", e.getMessage());
        }
    }

    // A partir de la seleccion de un objeto sobre la tabla, Se cargan sus datos en los elementos de la pantalla
    private void cargarDatos() {
        productorSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (productorSeleccionado != null) {
            etiquetaInteractiva = new Label("Está seleccionado el Prodcutor con id: ");
            etiquetaId.setText(String.valueOf(productorSeleccionado.getId_productor()));
            entradaNombres.setText(productorSeleccionado.getNombres());
            entradaApellidos.setText(productorSeleccionado.getApellidos());
            entradaDni.setText(productorSeleccionado.getDni());
           
            // este se usaria en Vista_ABM_Cuadro, y Vista_ABM_Lote
            // departamentos.getSelectionModel().select(productorSeleccionado.getDepartamento());
        }
    }


    private void clicEliminarProductor(){
        productorSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (productorSeleccionado != null) {
            servicio.eliminarProductor(productorSeleccionado.getId_productor());
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
        tabla.getItems().addAll(this.servicio.listarProductores());
    } 
}
