package cl.duoc.dsy1102.timecapsule;

import cl.duoc.dsy1102.timecapsule.dao.JsonMensajeDao;
import cl.duoc.dsy1102.timecapsule.dao.MensajeDao;
import cl.duoc.dsy1102.timecapsule.model.MensajeFuturo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    private Stage primaryStage;
    private final MensajeDao mensajeDao = new JsonMensajeDao(Path.of("data", "mensajes.json"));
    private List<MensajeFuturo> mensajes = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        try {
            this.mensajes = new ArrayList<>(mensajeDao.listar());
        } catch (IOException e) {
            this.mensajes = new ArrayList<>();
        }

        mostrarListado();
    }

    public void mostrarListado() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        HelloController controller = fxmlLoader.getController();
        controller.configurar(this);

        primaryStage.setTitle("Cápsula del Tiempo FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void mostrarFormulario() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("formulario-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        FormularioController controller = fxmlLoader.getController();
        controller.configurar(this);

        primaryStage.setTitle("Nuevo Mensaje - Cápsula del Tiempo FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public List<MensajeFuturo> getMensajes() {
        return mensajes;
    }

    public MensajeDao getMensajeDao() {
        return mensajeDao;
    }

    public static void main(String[] args) {
        launch();
    }
}