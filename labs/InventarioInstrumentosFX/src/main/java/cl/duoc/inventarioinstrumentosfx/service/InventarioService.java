package cl.duoc.inventarioinstrumentosfx.service;

import cl.duoc.inventarioinstrumentosfx.dao.InstrumentoDao;
import cl.duoc.inventarioinstrumentosfx.model.Instrumento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public class InventarioService {
    private final InstrumentoDao dao;
    private final ObservableList<Instrumento> datos =
            FXCollections.observableArrayList();

    public InventarioService(InstrumentoDao dao) {
        this.dao = dao;
    }

    public ObservableList<Instrumento> listar() {
        return datos;
    }

    public void cargar() throws IOException {
        var recuperados = dao.cargar();
        datos.setAll(recuperados);
    }

    public void registrar(String codigo, String nombre, String categoria)
            throws IOException {
        codigo = codigo == null ? "" : codigo.trim();
        nombre = nombre == null ? "" : nombre.trim();
        categoria = categoria == null ? "" : categoria.trim();

        if (codigo.isBlank() || nombre.isBlank() || categoria.isBlank()) {
            throw new IllegalArgumentException("Completa los tres campos");
        }
        for (Instrumento actual : datos) {
            if (actual.getCodigo().equalsIgnoreCase(codigo)) {
                throw new IllegalArgumentException("Código repetido");
            }
        }

        var nuevos = new ArrayList<>(datos);
        nuevos.add(new Instrumento(codigo, nombre, categoria));
        dao.guardar(nuevos);
        datos.setAll(nuevos);
    }
}