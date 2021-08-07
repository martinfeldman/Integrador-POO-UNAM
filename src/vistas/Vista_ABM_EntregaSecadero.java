package vistas;
import modelo.Cosecha;
import modelo.EntregaSecadero;
import servicios.Servicio_Cosechas;
import servicios.Servicio_EntregasSecadero;

import java.time.LocalDate;
import java.util.ArrayList;
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

public class Vista_ABM_EntregaSecadero implements Vista {
    
    // el servicio con el que se va a comunicar la vista
    private final Servicio_EntregasSecadero servicio;
    Servicio_Cosechas servicio_Cosechas;
    private EntregaSecadero entregaSeleccionada;
       
    // objetos de la pantalla
    TableView<EntregaSecadero> tabla;
    TableColumn<EntregaSecadero, Integer> columnaId;
    TableColumn<EntregaSecadero, LocalDate> columnaFecha;
    TableColumn<EntregaSecadero, ArrayList<Cosecha>> columnaCosechas;
    TableColumn<EntregaSecadero, Double> columnaPesoSecadero;
    TableColumn<EntregaSecadero, Double> columnaPesoCampo;
    Button botonAgregar, botonEliminar, botonLimpiar;
    TextField entradaNombres, entradaApellidos, entradaDni;
    Label etiquetaId, etiquetaInteractiva;

    // objetos para tomar los datos
    ComboBox<Cosecha> cosechasBox;
    DatePicker datepicker;
    Label etiquetaEntrega, etiquetaComboBox_cosechas, etiquetaFecha, etiquetaPesoSecadero;
    TextField entradaPesoSecadero; 

    public Vista_ABM_EntregaSecadero(Servicio_EntregasSecadero servicio, Servicio_Cosechas servicio_Cosechas){
        this.servicio = servicio;
        this.servicio_Cosechas = servicio_Cosechas;
    }

    @Override
    public Parent obtenerVista() {

    // definicion elementos de pantalla 

        VBox contenedor = new VBox();
        HBox contenedorBotones = new HBox();
        VBox contenedorCarga = new VBox();
     
        etiquetaId = new Label("");
        etiquetaInteractiva = new Label("Puede seleccionar filas de la tabla para editarlas.\nDeberá agregar cosechas a una entrega determinada luego de haberla seleccionado de la tabla");
        etiquetaComboBox_cosechas = new Label ("Seleccione una cosecha que haya en la entrega");
        etiquetaEntrega = new Label ("");
        etiquetaFecha = new Label ("Fecha de Entrega:");
        etiquetaPesoSecadero = new Label ("Peso en Secadero");

        datepicker = new DatePicker();

        entradaPesoSecadero = new TextField();

        cosechasBox = new ComboBox<>();
        tabla = new TableView<>(); 

        botonAgregar = new Button("Agregar/Modificar Selección");
        botonEliminar = new Button("Eliminar");
        botonLimpiar = new Button("Limpiar");

        columnaId = new TableColumn<>("Id");
        columnaFecha = new TableColumn<>("Fecha");
        columnaCosechas = new TableColumn<>("Cosechas");
        columnaPesoSecadero = new TableColumn<>("Peso Secadero");
        columnaPesoCampo = new TableColumn<>("Peso Campo");
       

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
        columnaCosechas.setMinWidth(350);
        columnaPesoSecadero.setMinWidth(200);
        columnaPesoCampo.setMinWidth(200);

        columnaId.setCellValueFactory(new PropertyValueFactory<>("id_Cuadro"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaCosechas.setCellValueFactory(new PropertyValueFactory<>("cosechas"));
        columnaPesoSecadero.setCellValueFactory(new PropertyValueFactory<>("pesoSecadero"));
        columnaPesoCampo.setCellValueFactory(new PropertyValueFactory<>("pesoCampo"));

   

    // acciones sobre elementos 
    
        botonAgregar.setOnAction(e -> clicAgregarEntrega());
        botonEliminar.setOnAction(e -> clicEliminarEntrega());
        botonLimpiar.setOnAction(e -> limpiar());
        //empleadoBox.setOnAction(e -> cambiarEtiquetaEmpleado());

        cosechasBox.getItems().addAll(servicio_Cosechas.listarCosechas());
        tabla.getItems().addAll(this.servicio.listarEntregaSecadero());


        //- asociamos las columnas a la tabla
        tabla.getColumns().add(columnaId);
        tabla.getColumns().add(columnaFecha);
        tabla.getColumns().add(columnaCosechas);
        tabla.getColumns().add(columnaPesoSecadero);
        tabla.getColumns().add(columnaPesoCampo);
        tabla.getSelectionModel().selectedItemProperty().addListener(e -> cargarDatos());

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonAgregar, botonEliminar, botonLimpiar);
        contenedorCarga.getChildren().addAll(contenedorBotones, etiquetaInteractiva , etiquetaId, 
         etiquetaFecha, datepicker, etiquetaComboBox_cosechas, cosechasBox, etiquetaPesoSecadero, entradaPesoSecadero);
        contenedor.getChildren().addAll(tabla, contenedorCarga);

        return contenedor;
    }




    private void clicAgregarEntrega() {
        // asumo selección simple
        entregaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        try {
            if (entregaSeleccionada == null) {
                // Si no hay elemento seleccionado en la tabla
                // Faltan completar los parametros
              //  servicio.agregarEntregaSecadero(cosechasBox.getValue(), datepicker.getValue());
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
        entregaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (entregaSeleccionada != null) {
            etiquetaId.setText(String.valueOf(entregaSeleccionada.getId_Entrega()));
            //entradaNombres.setText(cuadroSeleccionado.getNombres());
           // entradaApellidos.setText(cuadroSeleccionado.getApellidos());
           // departamentos.getSelectionModel().select(cuadroSeleccionado.getDepartamento());
        }
    }


 
    private void clicEliminarEntrega() {
        entregaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (entregaSeleccionada != null) {
            servicio.eliminarEntregaSecadero(entregaSeleccionada.getId_Entrega());
            limpiar();
        }
    }

    
    private void limpiar() {
        etiquetaId.setText("");
        etiquetaInteractiva.setText("Puede seleccionar filas de la tabla para editarlas.\nDeberá agregar cosechas a una entrega determinada luego de haberla seleccionado de la tabla");
       // entradaCuadro.clear();
       // entradaKgsCosechados.clear();
 
        tabla.getItems().clear();
        tabla.getItems().addAll(this.servicio.listarEntregaSecadero());
    } 


   /* private void cambiarEtiquetaEmpleado() {
        etiquetaEmpleado.setText("Esta cosecha fue realizada por el empleado \n" + empleadoBox.getValue().getId_Empleado());
    }  */



}
