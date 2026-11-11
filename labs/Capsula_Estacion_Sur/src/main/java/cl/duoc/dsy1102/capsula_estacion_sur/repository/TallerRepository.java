package cl.duoc.dsy1102.capsula_estacion_sur.repository;

import cl.duoc.dsy1102.capsula_estacion_sur.model.Taller;
import java.util.List;

public interface TallerRepository {
    void guardar(List<Taller> talleres);
    List<Taller> cargar();
}
