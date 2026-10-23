package cl.duoc.inventarioinstrumentosfx.dao;

import cl.duoc.inventarioinstrumentosfx.model.Instrumento;
import java.io.IOException;
import java.util.List;

public interface InstrumentoDao {
    List<Instrumento> cargar() throws IOException;
    void guardar(List<Instrumento> instrumentos) throws IOException;
}