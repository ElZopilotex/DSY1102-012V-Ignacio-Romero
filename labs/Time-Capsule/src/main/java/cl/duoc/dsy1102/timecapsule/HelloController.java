package cl.duoc.dsy1102.timecapsule;

import cl.duoc.dsy1102.timecapsule.model.MensajeFuturo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HelloController {

    @FXML private TableView<MensajeFuturo> tablaMensajes;
    @FXML private TableColumn<MensajeFuturo, String> colAutor;
    @FXML private TableColumn<MensajeFuturo, String> colTexto;
    @FXML private TableColumn<MensajeFuturo, String> colPlazo;

    private final ObservableList<MensajeFuturo> mensajes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
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