import repositorios.Repositorio;
import servicios.*;
import vistas.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class App extends Application {

    // definicion cambiante
    Group cambiante = new Group();

    // definicion de vistas
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmpresaVerdePU");

    Servicio_Productores servicio_productores = new Servicio_Productores(new Repositorio(emf));
    Vista_ABM_Productor vistaProductores = new Vista_ABM_Productor(servicio_productores) ;
    
    Servicio_Lotes servicio_lotes = new Servicio_Lotes(new Repositorio(emf));
    Vista_ABM_Lote vistaLotes = new Vista_ABM_Lote(servicio_lotes);

   // Vista_ABM_Lote vistaLotes;
    Vista_ABM_Cuadro vistaCuadros;
    Vista_ABM_Empleado vistaEmpleados;
    Vista_ABM_Cosecha vistaCosechas;
    Vista_ABM_EntregaSecadero vistaEntregasSecadero;
    

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // defino persistencia (Esto queda muy dependiente de JPA)
        // si quiero hacer menos dependiente hay que cambiar el formato del repositorio
        // hacer una interfaz y ahi crear los distintos tipos de repositorios
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmpresaVerdePU");

        // definicion de servicios
        // var servicio_productores = new Servicio_Productores(new Repositorio(emf));
        servicio_lotes = new Servicio_Lotes(new Repositorio(emf));
        // var servicio_cuadros = new Servicio_Cuadros(new Repositorio(emf));
        // var servicio_empleado = new Servicio_Empleados(new Repositorio(emf));
        // var servicio_seguimiento = new Servicio_seguimientoEmpleado(new Repositorio(emf));
        // var servicio_cosechas = new Servicio_Cosechas(new Repositorio(emf));
        // var servicio_entregasSecadero = new Servicio_EntregasSecadero(new Repositorio(emf));

        // asignacion de vistas, las cuales necesitan un servicio
        // var vistaProductores = new Vista_ABM_Productor(servicio_productores);
        // var vistaLotes = new Vista_ABM_Lote(servicio_lotes);
        // var vistaCuadros = new Vista_ABM_Cuadro(servicio_cuadros);
        // var vistaEmpleados = new Vista_ABM_Empleado(servicio_empleado, servicio_seguimiento);
        // var vistaCosechas = new Vista_ABM_Cosecha(servicio_cosechas);
        // var vistaEntregasSecadero = new Vista_ABM_EntregaSecadero(servicio_entregasSecadero);



        // definicion elementos de pantalla

        VBox contenedorBotones = new VBox();
        HBox contenedorPrincipal = new HBox();
        Scene escena = new Scene(contenedorPrincipal, 1200, 600);

        Button botonProductores = new Button("Productores");
        Button botonLotes = new Button("Lotes");
        Button botonCuadros = new Button("Cuadros");
        Button botonEmpleados = new Button("Empleados");
        Button botonCosechas = new Button("Cosechas");
        Button botonEntregasSecadero = new Button("EntregasSecadero");

        Separator separador1 = new Separator(Orientation.HORIZONTAL);
        Separator separador2 = new Separator(Orientation.HORIZONTAL);
        Separator separador3 = new Separator(Orientation.HORIZONTAL);
        Separator separador4 = new Separator(Orientation.HORIZONTAL);
        Separator separador5 = new Separator(Orientation.HORIZONTAL);
        Separator separadorX = new Separator(Orientation.VERTICAL);        


        
        // propiedades de elementos
        
        stage.setTitle("Empresa - Productores");
        stage.setResizable(false);

        contenedorBotones.setPadding(new Insets(10, 10, 10, 10));
        contenedorBotones.setSpacing(10);
        //contenedorBotones.setAlignment(Pos.CENTER);



        // acciones sobre elementos 
        botonProductores.setOnAction(e -> clicMostrarVistaProductores(stage));
        botonLotes.setOnAction(e -> clicMostrarVistaLotes(stage));
        botonCuadros.setOnAction(e -> clicMostrarVistaCuadros(stage));
        botonEmpleados.setOnAction(e -> clicMostrarVistaEmpleados(stage));
        botonCosechas.setOnAction(e -> clicMostrarVistaCosechas(stage));
        botonEntregasSecadero.setOnAction(e -> clicMostrarVistaEntregaSecadero(stage));

        //- agregamos contenido a los contenedores
        contenedorBotones.getChildren().addAll(botonProductores, separador1, botonLotes, separador2,
         botonCuadros, separador3, botonEmpleados, separador4, botonCosechas, separador5, botonEntregasSecadero);

        // que muestro inicialmente : Creo que se puede dar vuelta el orden de las siguientes dos sentencias 
        cambiante.getChildren().add(vistaProductores.obtenerVista());
        contenedorPrincipal.getChildren().addAll(contenedorBotones, separadorX, cambiante);
        
        stage.setScene(escena);
        stage.show();     
    
    }


    // definicion de metodos disparados por clic

    /*  posibles propiedades para usar al invocar al stage
        stage.sizeToScene();
        stage.centerOnScreen(); */


    private void clicMostrarVistaProductores(Stage stage) {
        System.out.print(stage);
        cambiante.getChildren().clear();
        cambiante.getChildren().add(vistaProductores.obtenerVista());
        stage.setTitle("Empresa - Productores");
    }


    private void clicMostrarVistaLotes(Stage stage) {
        cambiante.getChildren().clear();
        cambiante.getChildren().add(vistaLotes.obtenerVista());
        stage.setTitle("Empresa - Lotes");
    }


    private void clicMostrarVistaCuadros(Stage stage) {
        cambiante.getChildren().clear();
        cambiante.getChildren().add(vistaCuadros.obtenerVista());
        stage.setTitle("Empresa - Cuadros");
    }


    private void clicMostrarVistaEmpleados(Stage stage) {
        cambiante.getChildren().clear();
        cambiante.getChildren().add(vistaEmpleados.obtenerVista());
        stage.setTitle("Empresa - Empleados");
    }


    private void clicMostrarVistaCosechas(Stage stage) {
        cambiante.getChildren().clear();
        cambiante.getChildren().add(vistaCosechas.obtenerVista());
        stage.setTitle("Empresa - Cosechas");
    }


    private void clicMostrarVistaEntregaSecadero(Stage stage) {
        cambiante.getChildren().clear();
        cambiante.getChildren().add(vistaEntregasSecadero.obtenerVista());
        stage.setTitle("Empresa - Entregas al Secadero");
    }


}
