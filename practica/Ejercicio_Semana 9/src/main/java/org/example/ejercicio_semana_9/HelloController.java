package org.example.ejercicio_semana_9;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEdad;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private Label lblFeedback;

    @FXML
    public void initialize() {
        cmbCategoria.getItems().addAll("Perro", "Gato", "Ave", "Exótico");
    }

    @FXML
    private void onGuardarButtonClick() {
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();
        String categoria = cmbCategoria.getValue();

        if (nombre.isEmpty() || edadStr.isEmpty() || categoria == null) {
            lblFeedback.setStyle("-fx-text-fill: red;");
            lblFeedback.setText("Error: Por favor completa todos los campos.");
            return;
        }

        try {
            int edad = Integer.parseInt(edadStr);
            if (edad <= 0) {
                lblFeedback.setStyle("-fx-text-fill: red;");
                lblFeedback.setText("Error: La edad debe ser un entero positivo mayor a 0.");
                return;
            }

            Mascota mascota = new Mascota(nombre, edad, categoria);

            lblFeedback.setStyle("-fx-text-fill: green;");
            lblFeedback.setText("¡Registro exitoso!\n" + mascota.toString());

        } catch (NumberFormatException e) {
            lblFeedback.setStyle("-fx-text-fill: red;");
            lblFeedback.setText("Error: La edad debe ser un número entero válido.");
        }
    }

    @FXML
    private void onLimpiarButtonClick() {
        txtNombre.clear();
        txtEdad.clear();
        cmbCategoria.getSelectionModel().clearSelection();
        lblFeedback.setText("");
    }
}