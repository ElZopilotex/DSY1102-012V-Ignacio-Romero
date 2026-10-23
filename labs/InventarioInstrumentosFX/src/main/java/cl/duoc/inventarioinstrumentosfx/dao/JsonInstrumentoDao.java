package cl.duoc.inventarioinstrumentosfx.dao;

import cl.duoc.inventarioinstrumentosfx.model.Instrumento;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class JsonInstrumentoDao implements InstrumentoDao {
    private final Path archivo;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonInstrumentoDao(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Instrumento> cargar() throws IOException {
        if (Files.notExists(archivo)) {
            return List.of();
        }
        return mapper.readValue(
                archivo.toFile(),
                new TypeReference<List<Instrumento>>() {}
        );
    }

    @Override
    public void guardar(List<Instrumento> instrumentos) throws IOException {
        Path directorio = archivo.toAbsolutePath().getParent();
        if (directorio != null) {
            Files.createDirectories(directorio);
        }
        // Para esta práctica pequeña guardamos la lista JSON completa.
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(archivo.toFile(), instrumentos);
    }
}
