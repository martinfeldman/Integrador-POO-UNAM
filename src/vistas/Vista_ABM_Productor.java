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
        
       // definicion elementos de pantalla
     
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas");

        entradaNombres = new TextField();
        entradaApellidos = new TextField();
        entradaDni = new TextField();
        
        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");
    
        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();

        columnaId = new TableColumn<>("Id");
        columnaDni = new TableColumn<>("DNI");
        columnaNombres = new TableColumn<>("Nombres");
        columnaApellidos = new TableColumn<>("Apellidos");

        tabla = new TableView<>();



        // propiedades de elementos

        tabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tabla.setPrefHeight(300);

        entradaNombres.setPromptText("Nombres del productor");
        entradaApellidos.setPromptText("Apellidos del productor");
        entradaDni.setPromptText("DNI del productor");

        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorCarga.setPadding(new Insets(10, 10, 10, 10));
        contenedorBotones.setSpacing(10);
        contenedorCarga.setSpacing(10);

        // COLUMNAS - propiedades
        columnaId.setMinWidth(100);
        columnaDni.setMinWidth(200);
        columnaNombres.setMinWidth(300);
        columnaApellidos.setMinWidth(300);
        
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Productor"));
        columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        columnaNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));


        // acciones sobre elementos 

        botonAgregar.setOnAction(e -> clicAgregarCuadro());
        botonEliminar.setOnAction(e -> clicEliminarCuadro());
        botonLimpiar.setOnAction(e -> limpiar());

        // pedimos los datos de Productores a la BD y mostramos en tabla 
        tabla.getItems().addAll(this.servicio.listarProductores());

        // agregamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaDni);
        tabla.getColumns().add(columnaNombres);
        tabla.getColumns().add(columnaApellidos);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());
       
        // agregamos al contenedor la tabla
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);    
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva , etiquetaId, entradaNombres, entradaApellidos, entradaDni);
        contenedor.getChildren().addAll(tabla, contenedorCarga);
        
        return contenedor;

    }



        // como mostrar los lotes y cuadros de un productor? 
        // Podria crear otra tabla debajo que lea el productorSeleccionado 
        // en la tabla de arriba y haga la busqueda. 


 


    private void clicAgregarCuadro(){
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



    // A partir de la seleccion de un objeto sobre la tabla, se cargan sus datos en los elementos de la pantalla
    private void cargarDatos() {
        productorSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (productorSeleccionado != null) {
            etiquetaInteractiva.setText("Está seleccionado el Prodcutor con id: ");
            etiquetaId.setText(String.valueOf(productorSeleccionado.getId_productor()));
            entradaNombres.setText(productorSeleccionado.getNombres());
            entradaApellidos.setText(productorSeleccionado.getApellidos());
            entradaDni.setText(productorSeleccionado.getDni());
           
            // este se usaria en Vista_ABM_Cuadro, y Vista_ABM_Lote
            // departamentos.getSelectionModel().select(productorSeleccionado.getDepartamento());
        }
    }


    private void clicEliminarCuadro(){
        productorSeleccionado = tabla.getSelectionModel().getSelectedItem();
        if (productorSeleccionado != null) {
            servicio.eliminarProductor(productorSeleccionado.getId_productor());
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
        tabla.getItems().addAll(this.servicio.listarProductores());
    } 
}
