package cl.duoc.viviendas.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> void guardar(List<T> lista, String archivo) {
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar en " + archivo);
        }
    }

    public static <T> List<T> cargar(String archivo, Type tipoLista) {
        File file = new File(archivo);
        if (!file.exists()) return new ArrayList<>();

        try (FileReader reader = new FileReader(archivo)) {
            List<T> datos = gson.fromJson(reader, tipoLista);
            return datos != null ? datos : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}