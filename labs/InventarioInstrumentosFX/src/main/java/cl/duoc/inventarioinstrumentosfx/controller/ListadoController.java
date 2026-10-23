package cl.duoc.inventarioinstrumentosfx.controller;

import cl.duoc.inventarioinstrumentosfx.model.Instrumento;
import cl.duoc.inventarioinstrumentosfx.service.InventarioService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListadoController {
    @FXML private TableView<Instrumento> tabla;
    @FXML private TableColumn<Instrumento, String> colCodigo;
    @FXML private TableColumn<Instrumento, String> colNombre;
    @FXML private TableColumn<Instrumento, String> colCategoria;
    private Runnable abrirFormulario;

    public void configurar(InventarioService servicio, Runnable abrirFormulario) {
        this.abrirFormulario = abrirFormulario;
        colCodigo.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(c.getValue().getNombre()));
        colCategoria.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(c.getValue().getCategoria()));
        tabla.setItems(servicio.listar());
    }

    @FXML private void irFormulario() {
        abrirFormulario.run();
    }
}