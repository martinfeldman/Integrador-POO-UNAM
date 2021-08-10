package vistas;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface Vista {
    public Parent obtenerVista();

    public default void mostrarAlerta(AlertType tipo, String titulo, String cabecera, String mensaje) {
        
        // mostramos una alerta
        var alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
