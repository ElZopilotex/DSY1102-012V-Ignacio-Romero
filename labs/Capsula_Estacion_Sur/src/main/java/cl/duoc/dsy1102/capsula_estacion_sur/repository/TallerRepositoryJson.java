package cl.duoc.dsy1102.capsula_estacion_sur.repository;

import cl.duoc.dsy1102.capsula_estacion_sur.model.Taller;
import cl.duoc.dsy1102.capsula_estacion_sur.model.TallerPresencial;
import cl.duoc.dsy1102.capsula_estacion_sur.model.TallerVirtual;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TallerRepositoryJson implements TallerRepository {

    private final String FILE_PRESENCIAL = "talleres_presenciales.json";
    private final String FILE_VIRTUAL = "talleres_virtuales.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void guardar(List<Taller> talleres) {
        List<TallerPresencial> presenciales = new ArrayList<>();
        List<TallerVirtual> virtuales = new ArrayList<>();

        for (Taller t : talleres) {
            if (t instanceof TallerPresencial) {
                presenciales.add((TallerPresencial) t);
            } else if (t instanceof TallerVirtual) {
                virtuales.add((TallerVirtual) t);
            }
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PRESENCIAL), presenciales);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_VIRTUAL), virtuales);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<Taller> cargar() {
        List<Taller> todos = new ArrayList<>();

        try {
            File filePresencial = new File(FILE_PRESENCIAL);
            if (filePresencial.exists()) {
                List<TallerPresencial> p = mapper.readValue(filePresencial, new TypeReference<List<TallerPresencial>>() {});
                todos.addAll(p);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar presenciales: " + e.getMessage());
        }

        try {
            File fileVirtual = new File(FILE_VIRTUAL);
            if (fileVirtual.exists()) {
                List<TallerVirtual> v = mapper.readValue(fileVirtual, new TypeReference<List<TallerVirtual>>() {});
                todos.addAll(v);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar virtuales: " + e.getMessage());
        }

        return todos;
    }
}
