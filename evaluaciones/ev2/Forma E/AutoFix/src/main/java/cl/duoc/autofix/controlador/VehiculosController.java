package cl.duoc.autofix.controlador;

import cl.duoc.autofix.dao.VehiculoDAO;
import cl.duoc.autofix.model.Auto;
import cl.duoc.autofix.model.Furgon;
import cl.duoc.autofix.model.Vehiculo;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VehiculosController {

    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtMarca, txtAnyo, txtKilometraje, txtModelo, txtCapacidad;
    @FXML private CheckBox chkGarantiaVigente, chkGarantiaActiva;
    @FXML private TitledPane paneAuto, paneFurgon;
    @FXML private Label lblMensaje;

    @FXML private TableView<Vehiculo> tblVehiculos;
    @FXML private TableColumn<Vehiculo, String> colTipo, colMarca;
    @FXML private TableColumn<Vehiculo, Integer> colAnyo, colKilometraje;
    @FXML private TableColumn<Vehiculo, String> colCosto;

    private final VehiculoDAO dao = new VehiculoDAO();
    private final ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cmbTipo.setItems(FXCollections.observableArrayList("Auto", "Furgon"));
        configurarColumnas();
        cargarDatos();

        cmbTipo.setOnAction(e -> {
            boolean isAuto = "Auto".equals(cmbTipo.getValue());
            boolean isFurgon = "Furgon".equals(cmbTipo.getValue());
            paneAuto.setDisable(!isAuto);
            paneFurgon.setDisable(!isFurgon);
        });
    }

    private void configurarColumnas() {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        colMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        colAnyo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnioFabricacion()).asObject());
        colKilometraje.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getKilometraje()).asObject());
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf((long) cellData.getValue().calcularCostoServicio())));
        tblVehiculos.setItems(listaVehiculos);
    }

    private void cargarDatos() {
        listaVehiculos.setAll(dao.cargarVehiculos());
    }

    @FXML
    private void registrarVehiculo() {
        try {
            String tipo = cmbTipo.getValue();
            if (tipo == null) throw new IllegalArgumentException("Debe seleccionar un tipo de vehículo.");

            String marca = txtMarca.getText();
            int anyo = Integer.parseInt(txtAnyo.getText());
            int kilometraje = Integer.parseInt(txtKilometraje.getText());

            Vehiculo nuevoVehiculo;

            if ("Auto".equals(tipo)) {
                String modelo = txtModelo.getText();
                boolean garVigente = chkGarantiaVigente.isSelected();
                boolean garActiva = chkGarantiaActiva.isSelected();
                nuevoVehiculo = new Auto(marca, anyo, kilometraje, modelo, garVigente);
                if(garActiva) ((Auto)nuevoVehiculo).activarGarantia();
            } else {
                double capacidad = Double.parseDouble(txtCapacidad.getText().replace(",", "."));
                nuevoVehiculo = new Furgon(marca, anyo, kilometraje, capacidad);
            }

            listaVehiculos.add(nuevoVehiculo);
            dao.guardarVehiculos(listaVehiculos);
            mostrarMensaje("Vehículo registrado correctamente.", false);
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Verifique que los campos numéricos sean correctos.", true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
        } catch (Exception e) {
            mostrarMensaje("Error al registrar: " + e.getMessage(), true);
        }
    }

    @FXML
    private void eliminarVehiculo() {
        Vehiculo seleccionado = tblVehiculos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaVehiculos.remove(seleccionado);
            dao.guardarVehiculos(listaVehiculos);
            mostrarMensaje("Vehículo eliminado correctamente.", false);
        } else {
            mostrarMensaje("Debe seleccionar un vehículo de la tabla para eliminarlo.", true);
        }
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    private void limpiarFormulario() {
        cmbTipo.getSelectionModel().clearSelection();
        txtMarca.clear();
        txtAnyo.clear();
        txtKilometraje.clear();
        txtModelo.clear();
        txtCapacidad.clear();
        chkGarantiaVigente.setSelected(false);
        chkGarantiaActiva.setSelected(false);
        paneAuto.setDisable(true);
        paneFurgon.setDisable(true);
    }
}