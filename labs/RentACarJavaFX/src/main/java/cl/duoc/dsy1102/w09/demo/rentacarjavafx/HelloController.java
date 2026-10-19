package cl.duoc.dsy1102.w09.demo.rentacarjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML private TextField patenteField;
    @FXML private Label resultadoLabel;
    @FXML private TextField marcaField;

    @FXML
    private void registrar() {
        try {
            Vehiculo vehiculo = new Vehiculo(patenteField.getText(), marcaField.getText());
            resultadoLabel.setText("Vehiculo disponible: " + vehiculo.getPatente() + " - Marca: " + vehiculo.getMarca());
        } catch (IllegalArgumentException exception) {
            resultadoLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        patenteField.clear();
        marcaField.clear();
        resultadoLabel.setText("Completa el formulario");
    }
}