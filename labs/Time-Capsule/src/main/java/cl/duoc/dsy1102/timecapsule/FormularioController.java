package cl.duoc.dsy1102.timecapsule;

import cl.duoc.dsy1102.timecapsule.model.MensajeFuturo;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FormularioController {

    @FXML private TextField campoAutor;
    @FXML private TextField campoTexto;
    @FXML private ComboBox<Integer> campoPlazo;
    @FXML private Label estadoRegistro;

    private HelloApplication app;

    public void configurar(HelloApplication app) {
        this.app = app;
    }

    @FXML
    private void initialize() {
        campoPlazo.getItems().addAll(1, 3, 5, 10, 20);
    }

    @FXML
    private void guardarMensaje() throws IOException {
        String autor = campoAutor.getText() != null ? campoAutor.getText().trim() : "";
        String texto = campoTexto.getText() != null ? campoTexto.getText().trim() : "";

        if (autor.isBlank() || texto.isBlank() || campoPlazo.getValue() == null) {
            estadoRegistro.setText("Debes completar el autor, mensaje y plazo.");
            return;
        }

        MensajeFuturo nuevoMensaje = new MensajeFuturo(autor, texto, campoPlazo.getValue());
        app.getMensajes().add(nuevoMensaje);
        app.getMensajeDao().guardar(app.getMensajes());

        app.mostrarListado();
    }

    @FXML
    private void volver() throws IOException {
        app.mostrarListado();
    }
}