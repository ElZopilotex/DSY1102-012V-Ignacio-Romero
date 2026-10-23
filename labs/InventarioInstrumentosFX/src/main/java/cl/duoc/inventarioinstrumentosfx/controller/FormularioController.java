package cl.duoc.inventarioinstrumentosfx.controller;

import cl.duoc.inventarioinstrumentosfx.service.InventarioService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class FormularioController {
    @FXML private TextField codigo;
    @FXML private TextField nombre;
    @FXML private ComboBox<String> categoria;
    @FXML private Label mensaje;
    private InventarioService servicio;
    private Runnable volverALlistado;

    @FXML private void initialize() {
        categoria.getItems().addAll("Guitarra", "Bajo", "Batería", "Teclado");
    }

    public void configurar(InventarioService servicio, Runnable volverALlistado) {
        this.servicio = servicio;
        this.volverALlistado = volverALlistado;
    }

    @FXML private void guardar() {
        try {
            servicio.registrar(codigo.getText(), nombre.getText(),
                    categoria.getValue());
            volverALlistado.run();
        } catch (IllegalArgumentException e) {
            mensaje.setText("Dato inválido: " + e.getMessage());
        } catch (IOException e) {
            mensaje.setText("No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    @FXML private void volver() {
        volverALlistado.run();
    }
}