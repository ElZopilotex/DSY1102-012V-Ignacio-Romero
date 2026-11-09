package cl.duoc.dsy1102.timecapsule;

import cl.duoc.dsy1102.timecapsule.model.MensajeFuturo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class HelloController {

    @FXML private TableView<MensajeFuturo> tablaMensajes;
    @FXML private TableColumn<MensajeFuturo, String> colAutor;
    @FXML private TableColumn<MensajeFuturo, String> colTexto;
    @FXML private TableColumn<MensajeFuturo, String> colPlazo;

    private HelloApplication app;

    public void configurar(HelloApplication app) {
        this.app = app;
        tablaMensajes.setItems(FXCollections.observableArrayList(app.getMensajes()));
    }

    @FXML
    private void initialize() {
        colAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
        colTexto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTexto()));
        colPlazo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlazoAnios() + " años"));
    }

    @FXML
    private void abrirFormulario() throws IOException {
        app.mostrarFormulario();
    }
}