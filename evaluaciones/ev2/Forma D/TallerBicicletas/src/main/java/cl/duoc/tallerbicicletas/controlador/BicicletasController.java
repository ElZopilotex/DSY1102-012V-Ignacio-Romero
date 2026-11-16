package cl.duoc.tallerbicicletas.controlador;

import cl.duoc.tallerbicicletas.dao.BicicletaDAO;
import cl.duoc.tallerbicicletas.modelo.Bicicleta;
import cl.duoc.tallerbicicletas.modelo.BicicletaElectrica;
import cl.duoc.tallerbicicletas.modelo.BicicletaMontanya;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class BicicletasController {

    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtAnyo;
    @FXML private TextField txtPeso;

    @FXML private TitledPane paneElectrica;
    @FXML private TextField txtAutonomia;
    @FXML private CheckBox chkBateria;
    @FXML private CheckBox chkGarantia;

    @FXML private TitledPane paneMontanya;
    @FXML private TextField txtSuspensiones;

    @FXML private Label lblMensaje;

    @FXML private TableView<Bicicleta> tblBicicletas;
    @FXML private TableColumn<Bicicleta, String> colTipo;
    @FXML private TableColumn<Bicicleta, String> colCodigo;
    @FXML private TableColumn<Bicicleta, Integer> colAnyo;
    @FXML private TableColumn<Bicicleta, Double> colPeso;
    @FXML private TableColumn<Bicicleta, String> colCosto;

    private BicicletaDAO bicicletaDAO;
    private ObservableList<Bicicleta> listaObservable;

    @FXML
    public void initialize() {
        bicicletaDAO = new BicicletaDAO();
        listaObservable = FXCollections.observableArrayList(bicicletaDAO.obtenerBicicletas());

        cmbTipo.getItems().addAll("Bicicleta de Montaña", "Bicicleta Eléctrica");

        paneElectrica.setDisable(true);
        paneMontanya.setDisable(true);

        cmbTipo.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                if (newV.equals("Bicicleta de Montaña")) {
                    paneMontanya.setDisable(false);
                    paneElectrica.setDisable(true);
                    limpiarPanelElectrica();
                } else {
                    paneElectrica.setDisable(false);
                    paneMontanya.setDisable(true);
                    txtSuspensiones.clear();
                }
            }
        });

        colTipo.setCellValueFactory(cellData -> {
            Bicicleta b = cellData.getValue();
            String tipo = b instanceof BicicletaElectrica ? "Bicicleta Eléctrica" : "Bicicleta de Montaña";
            return new SimpleStringProperty(tipo);
        });

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        colAnyo.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(cellData.getValue().getAnioFabricacion());
        });

        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        colCosto.setCellValueFactory(cellData -> {
            Bicicleta b = cellData.getValue();
            int costo = (int) b.calcularCostoMantencion();
            String costoFormateado = String.format("$%,d", costo).replace(',', '.');
            return new SimpleStringProperty(costoFormateado);
        });

        tblBicicletas.setItems(listaObservable);
    }

    @FXML
    public void registrarBicicleta(ActionEvent event) {
        String tipo = cmbTipo.getValue();
        if (tipo == null) {
            mostrarError("Debe seleccionar un tipo de bicicleta.");
            return;
        }

        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarError("Código: no puede estar vacío.");
            return;
        }

        int año;
        try {
            año = Integer.parseInt(txtAnyo.getText().trim());
            if (año < 2000 || año > 2026) {
                mostrarError("Año de fabricación: debe ser un número entero entre 2000 y 2026.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("Año de fabricación: debe ser un número entero válido.");
            return;
        }

        double peso;
        try {
            peso = Double.parseDouble(txtPeso.getText().trim());
        } catch (NumberFormatException e) {
            mostrarError("Peso (kg): debe ser un número válido.");
            return;
        }

        Bicicleta nuevaBicicleta = null;

        if (tipo.equals("Bicicleta de Montaña")) {
            int suspensiones;
            try {
                suspensiones = Integer.parseInt(txtSuspensiones.getText().trim());
            } catch (NumberFormatException e) {
                mostrarError("Suspensiones: debe ser un número entero válido.");
                return;
            }
            nuevaBicicleta = new BicicletaMontanya(codigo, año, peso, suspensiones);
        } else if (tipo.equals("Bicicleta Eléctrica")) {
            int autonomia;
            try {
                autonomia = Integer.parseInt(txtAutonomia.getText().trim());
            } catch (NumberFormatException e) {
                mostrarError("Autonomía (km): debe ser un número entero válido.");
                return;
            }
            boolean bateria = chkBateria.isSelected();
            nuevaBicicleta = new BicicletaElectrica(codigo, año, peso, autonomia, bateria);
        }

        if (nuevaBicicleta != null) {
            listaObservable.add(nuevaBicicleta);
            bicicletaDAO.guardarBicicletas(listaObservable);
            mostrarExito(codigo + " (" + tipo + ") registrada correctamente.");
            limpiarFormulario();
        }
    }

    @FXML
    public void eliminarBicicleta(ActionEvent event) {
        Bicicleta seleccionada = tblBicicletas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarError("Debe seleccionar una bicicleta de la tabla para eliminarla.");
            return;
        }
        listaObservable.remove(seleccionada);
        bicicletaDAO.guardarBicicletas(listaObservable);
        mostrarExito("Bicicleta eliminada correctamente.");
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setTextFill(Color.RED);
        lblMensaje.setText(mensaje);
    }

    private void mostrarExito(String mensaje) {
        lblMensaje.setTextFill(Color.web("#007c0a"));
        lblMensaje.setText(mensaje);
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtAnyo.clear();
        txtPeso.clear();
        limpiarPanelElectrica();
        txtSuspensiones.clear();
        cmbTipo.getSelectionModel().clearSelection();
        paneElectrica.setDisable(true);
        paneMontanya.setDisable(true);
    }

    private void limpiarPanelElectrica() {
        txtAutonomia.clear();
        chkBateria.setSelected(false);
        chkGarantia.setSelected(false);
    }
}