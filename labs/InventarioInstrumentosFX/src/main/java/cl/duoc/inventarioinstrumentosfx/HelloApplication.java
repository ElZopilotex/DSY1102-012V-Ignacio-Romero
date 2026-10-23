package cl.duoc.inventarioinstrumentosfx;

import cl.duoc.inventarioinstrumentosfx.controller.FormularioController;
import cl.duoc.inventarioinstrumentosfx.controller.ListadoController;
import cl.duoc.inventarioinstrumentosfx.dao.JsonInstrumentoDao;
import cl.duoc.inventarioinstrumentosfx.service.InventarioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class HelloApplication extends Application {
    private final InventarioService servicio = new InventarioService(
            new JsonInstrumentoDao(Path.of("data", "instrumentos.json")));
    private Stage ventana;

    @Override
    public void start(Stage stage) {
        this.ventana = stage;
        stage.setTitle("Inventario musical");
        try {
            servicio.cargar();
            mostrarListado();
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,
                    "Error al leer datos. Se conservará el archivo: "
                            + e.getMessage()).showAndWait();
        }
    }

    private void mostrarListado() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("listado.fxml"));
        Parent root = loader.load();
        ListadoController controlador = loader.getController();
        controlador.configurar(servicio, this::abrirFormulario);
        ventana.setScene(new Scene(root));
    }

    private void mostrarFormulario() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("formulario.fxml"));
        Parent root = loader.load();
        FormularioController controlador = loader.getController();
        controlador.configurar(servicio, this::abrirListado);
        ventana.setScene(new Scene(root));
    }

    private void abrirFormulario() {
        try {
            mostrarFormulario();
        } catch (IOException e) {
            mostrarError(e);
        }
    }

    private void abrirListado() {
        try {
            mostrarListado();
        } catch (IOException e) {
            mostrarError(e);
        }
    }

    private void mostrarError(IOException e) {
        new Alert(Alert.AlertType.ERROR,
                "No se pudo cargar la vista: " + e.getMessage()).showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}