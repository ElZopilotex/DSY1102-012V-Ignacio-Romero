package cl.duoc.viviendas.controller;

import cl.duoc.viviendas.dao.GestorArchivos;
import cl.duoc.viviendas.model.*;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtCodigo, txtSuperficie, txtHabitaciones, txtPiso;
    @FXML private CheckBox chkPatio, chkGastoComun, chkEstacionamiento;
    @FXML private TitledPane paneCasa, paneDepto;
    @FXML private Label lblMensaje;

    @FXML private TableView<Vivienda> tablaViviendas;
    @FXML private TableColumn<Vivienda, String> colTipo, colCodigo, colSuperficie, colCosto;
    @FXML private TableColumn<Vivienda, Integer> colHabitaciones;

    private final ObservableList<Vivienda> listaViviendas = FXCollections.observableArrayList();
    private List<Casa> listaCasas = new ArrayList<>();
    private List<Departamento> listaDeptos = new ArrayList<>();

    @FXML
    public void initialize() {
        cmbTipo.getItems().addAll("Departamento", "Casa");
        cmbTipo.valueProperty().addListener((obs, oldVal, newVal) -> configurarFormulario(newVal));

        configurarColumnas();
        cargarDatos();
        tablaViviendas.setItems(listaViviendas);
    }

    private void configurarColumnas() {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));
        colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        colSuperficie.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSuperficie())));
        colHabitaciones.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHabitaciones()).asObject());
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty("$" + String.format("%,.0f", cellData.getValue().calcularCostoArriendo()).replace(",", ".")));
    }

    private void configurarFormulario(String tipo) {
        boolean esCasa = "Casa".equals(tipo);
        boolean esDepto = "Departamento".equals(tipo);

        paneCasa.setDisable(!esCasa);
        paneDepto.setDisable(!esDepto);
    }

    @FXML
    private void guardarVivienda() {
        try {
            String tipo = cmbTipo.getValue();
            if (tipo == null) {
                mostrarMensaje("Tipo: debe seleccionar una opción.", true);
                return;
            }

            String codigo = txtCodigo.getText().trim();
            if (codigo.isEmpty()) {
                mostrarMensaje("Código: no puede estar vacío.", true);
                return;
            }

            double superficie = Double.parseDouble(txtSuperficie.getText().replace(",", "."));
            if (superficie < 20 || superficie > 500) {
                mostrarMensaje("Superficie: debe ser un número entre 20 y 500.", true);
                return;
            }

            int habitaciones = Integer.parseInt(txtHabitaciones.getText());
            if (habitaciones <= 0) {
                mostrarMensaje("Habitaciones: debe ser un número entero mayor que cero.", true);
                return;
            }

            if ("Casa".equals(tipo)) {
                boolean patio = chkPatio.isSelected();
                Casa nuevaCasa = new Casa(codigo, superficie, habitaciones, patio);

                listaCasas.add(nuevaCasa);
                listaViviendas.add(nuevaCasa);
                GestorArchivos.guardar(listaCasas, "casas.json");

            } else if ("Departamento".equals(tipo)) {
                int piso = Integer.parseInt(txtPiso.getText());
                if (piso <= 0) {
                    mostrarMensaje("Piso: debe ser un número entero mayor que cero.", true);
                    return;
                }

                boolean gastoComun = chkGastoComun.isSelected();
                boolean est = chkEstacionamiento.isSelected();

                Departamento nuevoDepto = new Departamento(codigo, superficie, habitaciones, piso, gastoComun);
                if (est) {
                    nuevoDepto.asignarEstacionamiento();
                }

                listaDeptos.add(nuevoDepto);
                listaViviendas.add(nuevoDepto);
                GestorArchivos.guardar(listaDeptos, "departamentos.json");
            }

            mostrarMensaje(codigo + " (" + tipo + ") registrada correctamente.", false);
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Verifique que los campos numéricos sean válidos.", true);
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), true);
        }
    }

    @FXML
    private void eliminarVivienda() {
        Vivienda seleccionada = tablaViviendas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Debe seleccionar una vivienda de la tabla para eliminarla.", true);
            return;
        }

        listaViviendas.remove(seleccionada);
        if (seleccionada instanceof Casa) {
            listaCasas.remove(seleccionada);
            GestorArchivos.guardar(listaCasas, "casas.json");
        } else if (seleccionada instanceof Departamento) {
            listaDeptos.remove(seleccionada);
            GestorArchivos.guardar(listaDeptos, "departamentos.json");
        }

        mostrarMensaje("Vivienda eliminada correctamente.", false);
    }

    private void cargarDatos() {
        listaCasas = GestorArchivos.cargar("casas.json", new TypeToken<List<Casa>>(){}.getType());
        listaDeptos = GestorArchivos.cargar("departamentos.json", new TypeToken<List<Departamento>>(){}.getType());

        listaViviendas.addAll(listaCasas);
        listaViviendas.addAll(listaDeptos);
    }

    private void mostrarMensaje(String texto, boolean esError) {
        lblMensaje.setText(texto);
        lblMensaje.setStyle(esError ? "-fx-text-fill: red; -fx-font-weight: bold;" : "-fx-text-fill: green; -fx-font-weight: bold;");
    }

    private void limpiarCampos() {
        cmbTipo.getSelectionModel().clearSelection();
        txtCodigo.clear();
        txtSuperficie.clear();
        txtHabitaciones.clear();
        txtPiso.clear();
        chkPatio.setSelected(false);
        chkGastoComun.setSelected(false);
        chkEstacionamiento.setSelected(false);
        paneCasa.setDisable(true);
        paneDepto.setDisable(true);
    }
}