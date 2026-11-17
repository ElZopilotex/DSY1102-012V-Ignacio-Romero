package cl.duoc.autofix.dao;

import cl.duoc.autofix.model.Auto;
import cl.duoc.autofix.model.Furgon;
import cl.duoc.autofix.model.Vehiculo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private static final String ARCHIVO_AUTOS = "autos.json";
    private static final String ARCHIVO_FURGONES = "furgones.json";
    private final Gson gson = new Gson();

    public void guardarVehiculos(List<Vehiculo> vehiculos) {
        List<Auto> autos = new ArrayList<>();
        List<Furgon> furgones = new ArrayList<>();

        for (Vehiculo v : vehiculos) {
            if (v instanceof Auto) {
                autos.add((Auto) v);
            } else if (v instanceof Furgon) {
                furgones.add((Furgon) v);
            }
        }

        guardarEnArchivo(ARCHIVO_AUTOS, autos);
        guardarEnArchivo(ARCHIVO_FURGONES, furgones);
    }

    private void guardarEnArchivo(String archivo, List<?> lista) {
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar " + archivo + ": " + e.getMessage());
        }
    }

    public List<Vehiculo> cargarVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();

        List<Auto> autos = cargarDesdeArchivo(ARCHIVO_AUTOS, new TypeToken<List<Auto>>(){}.getType());
        if (autos != null) vehiculos.addAll(autos);

        List<Furgon> furgones = cargarDesdeArchivo(ARCHIVO_FURGONES, new TypeToken<List<Furgon>>(){}.getType());
        if (furgones != null) vehiculos.addAll(furgones);

        return vehiculos;
    }

    private <T> List<T> cargarDesdeArchivo(String archivo, Type tipo) {
        try (FileReader reader = new FileReader(archivo)) {
            return gson.fromJson(reader, tipo);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}