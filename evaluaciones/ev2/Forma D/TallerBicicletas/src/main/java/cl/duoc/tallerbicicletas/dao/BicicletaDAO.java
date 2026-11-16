package cl.duoc.tallerbicicletas.dao;

import cl.duoc.tallerbicicletas.modelo.Bicicleta;
import cl.duoc.tallerbicicletas.modelo.BicicletaElectrica;
import cl.duoc.tallerbicicletas.modelo.BicicletaMontanya;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BicicletaDAO {
    private static final String RUTA_ARCHIVO = "bicicletas.json";
    private Gson gson;

    public BicicletaDAO() {
        JsonDeserializer<Bicicleta> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has("autonomiaKm")) {
                return context.deserialize(json, BicicletaElectrica.class);
            } else {
                return context.deserialize(json, BicicletaMontanya.class);
            }
        };

        this.gson = new GsonBuilder()
                .registerTypeAdapter(Bicicleta.class, deserializer)
                .setPrettyPrinting()
                .create();
    }

    public void guardarBicicletas(List<Bicicleta> bicicletas) {
        try (Writer writer = new FileWriter(RUTA_ARCHIVO)) {
            gson.toJson(bicicletas, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Bicicleta> obtenerBicicletas() {
        try (Reader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Bicicleta>>(){}.getType();
            List<Bicicleta> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}