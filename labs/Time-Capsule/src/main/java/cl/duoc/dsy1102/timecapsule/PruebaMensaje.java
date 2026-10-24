package cl.duoc.dsy1102.timecapsule;

import cl.duoc.dsy1102.timecapsule.model.MensajeFuturo;
import java.util.ArrayList;
import java.util.List;

public class PruebaMensaje {

    static List<MensajeFuturo> listaMensajes = new ArrayList<>();

    public static void main(String[] args) {

        MensajeFuturo mensaje1 = new MensajeFuturo(
                "Valentina",
                "Nunca dejes de perseguir tus sueños.",
                5
        );

        MensajeFuturo mensaje2 = new MensajeFuturo(
                "Ignacio",
                "Recuerda revisar este código en 10 años.",
                10
        );

        listaMensajes.add(mensaje1);
        listaMensajes.add(mensaje2);

        for (MensajeFuturo mensaje : listaMensajes) {
            System.out.println("Autor: " + mensaje.getAutor());
            System.out.println("Mensaje: " + mensaje.getTexto());
            System.out.println("Leer dentro de: " + mensaje.getPlazoAnios() + " años");
            System.out.println("----------------------------------------");
        }
    }
}
