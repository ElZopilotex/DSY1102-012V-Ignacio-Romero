package cl.duoc.dsy1102.capsula_estacion_sur;

import cl.duoc.dsy1102.capsula_estacion_sur.model.Taller;
import cl.duoc.dsy1102.capsula_estacion_sur.model.TallerPresencial;
import cl.duoc.dsy1102.capsula_estacion_sur.model.TallerVirtual;
import cl.duoc.dsy1102.capsula_estacion_sur.repository.TallerRepository;
import cl.duoc.dsy1102.capsula_estacion_sur.repository.TallerRepositoryJson;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HelloController {

    @FXML private ComboBox<String> cmbModalidad;
    @FXML private VBox vboxPresencial;
    @FXML private VBox vboxVirtual;
    @FXML private TableView<Taller> tablaTalleres;
    @FXML private TableColumn<Taller, String> colCodigo;
    @FXML private TableColumn<Taller, String> colNombre;
    @FXML private TableColumn<Taller, String> colModalidad;
    @FXML private TableColumn<Taller, Integer> colArancel;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtCupos;
    @FXML private TextField txtPresencial1;
    @FXML private TextField txtPresencial2;
    @FXML private TextField txtVirtual1;
    @FXML private TextField txtVirtual2;
    @FXML private Label lblMensaje;
    @FXML private Button btnRegistrar;
    @FXML private Button btnEliminar;

    private ObservableList<Taller> listaTalleres = FXCollections.observableArrayList();
    private TallerRepository repositorio = new TallerRepositoryJson();

    @FXML
    public void initialize() {
        vboxPresencial.setVisible(false);
        vboxPresencial.setManaged(false);
        vboxVirtual.setVisible(false);
        vboxVirtual.setManaged(false);

        cmbModalidad.getItems().addAll("Presencial", "Virtual");

        cmbModalidad.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Presencial")) {
                    vboxPresencial.setVisible(true);
                    vboxPresencial.setManaged(true);
                    vboxVirtual.setVisible(false);
                    vboxVirtual.setManaged(false);
                } else if (newValue.equals("Virtual")) {
                    vboxVirtual.setVisible(true);
                    vboxVirtual.setManaged(true);
                    vboxPresencial.setVisible(false);
                    vboxPresencial.setManaged(false);
                }
            }
        });

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colModalidad.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof TallerPresencial) {
                return new SimpleStringProperty("Presencial");
            } else {
                return new SimpleStringProperty("Virtual");
            }
        });
        colArancel.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().calcularArancel()).asObject()
        );

        listaTalleres.addAll(repositorio.cargar());
        tablaTalleres.setItems(listaTalleres);

        btnRegistrar.setOnAction(event -> registrarActividad());
        btnEliminar.setOnAction(event -> eliminarActividad());
    }

    private void registrarActividad() {
        try {
            String modalidad = cmbModalidad.getValue();
            if (modalidad == null) {
                lblMensaje.setText("Error: Seleccione una modalidad primero.");
                return;
            }

            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();

            if (codigo.trim().isEmpty() || nombre.trim().isEmpty()) {
                lblMensaje.setText("Error: Código y Nombre no pueden estar vacíos.");
                return;
            }

            int duracion = Integer.parseInt(txtDuracion.getText());
            int cupos = Integer.parseInt(txtCupos.getText());

            Taller nuevoTaller;

            if (modalidad.equals("Presencial")) {
                String sala = txtPresencial1.getText();
                boolean materiales = Boolean.parseBoolean(txtPresencial2.getText());
                nuevoTaller = new TallerPresencial(codigo, nombre, duracion, cupos, sala, materiales);
            } else {
                String plataforma = txtVirtual1.getText();
                boolean datoExtraVirtual = Boolean.parseBoolean(txtVirtual2.getText());
                nuevoTaller = new TallerVirtual(codigo, nombre, duracion, cupos, plataforma, datoExtraVirtual);
            }

            listaTalleres.add(nuevoTaller);
            repositorio.guardar(new ArrayList<>(listaTalleres));
            lblMensaje.setText("Taller registrado y guardado con éxito.");

            txtCodigo.clear();
            txtNombre.clear();
            txtDuracion.clear();
            txtCupos.clear();
            txtPresencial1.clear();
            txtPresencial2.clear();
            txtVirtual1.clear();
            txtVirtual2.clear();

        } catch (NumberFormatException e) {
            lblMensaje.setText("Error: Verifique que los campos numéricos sean válidos.");
        } catch (Exception e) {
            lblMensaje.setText("Error al registrar: " + e.getMessage());
        }
    }

    private void eliminarActividad() {
        Taller seleccionado = tablaTalleres.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaTalleres.remove(seleccionado);
            repositorio.guardar(new ArrayList<>(listaTalleres));
            lblMensaje.setText("Taller eliminado correctamente.");
        } else {
            lblMensaje.setText("Error: Seleccione un taller de la tabla para eliminar.");
        }
    }
}
