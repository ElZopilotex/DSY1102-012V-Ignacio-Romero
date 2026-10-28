package cl.duoc.dsy1102.timecapsule;

import cl.duoc.dsy1102.timecapsule.model.MensajeFuturo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;


public class HelloController {

    @FXML private TableView<MensajeFuturo> tablaMensajes;
    @FXML private TableColumn<MensajeFuturo, String> colAutor;
    @FXML private TableColumn<MensajeFuturo, String> colTexto;
    @FXML private TableColumn<MensajeFuturo, String> colPlazo;
    @FXML private TextField campoAutor;
    @FXML private TextField campoTexto;
    @FXML private ComboBox<Integer> campoPlazo;
    @FXML private Label estadoRegistro;

    private final ObservableList<MensajeFuturo> mensajes = FXCollections.observableArrayList();

    @FXML
    private void agregarMensaje(){
        String autor = campoAutor.getText().trim();
        String texto = campoTexto.getText().trim();

        if (autor.isBlank() || texto.isBlank()) {
            estadoRegistro.setText("Debes completar el autor y el mensaje.");
            return;
        }

        MensajeFuturo nuevo = new MensajeFuturo(
                autor, texto, campoPlazo.getValue());
        mensajes.add(nuevo);
    }

    @FXML
    private void initialize() {
        campoPlazo.getItems().setAll(1, 5, 10);
        campoPlazo.setValue(5);

        colAutor.setCellValueFactory(cellDataFeatures ->
                new SimpleStringProperty(
                        cellDataFeatures.getValue().getAutor()
                )
        );

        colTexto.setCellValueFactory(cellDataFeatures ->
                new SimpleStringProperty(
                        cellDataFeatures.getValue().getTexto()
                )
        );

        colPlazo.setCellValueFactory(cellDataFeatures ->
                new SimpleStringProperty(
                        cellDataFeatures.getValue().getPlazoAnios() + " años"
                )
        );

        tablaMensajes.setItems(mensajes);

        mensajes.add(new MensajeFuturo(
                "Valentina", "Nunca dejes de perseguir tus sueños.", 5
        ));

        mensajes.add(new MensajeFuturo(
                "Carlos", "Recuerda de dónde comenzaste.", 10
        ));
    }
}