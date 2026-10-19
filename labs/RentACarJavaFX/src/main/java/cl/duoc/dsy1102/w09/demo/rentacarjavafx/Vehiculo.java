package cl.duoc.dsy1102.w09.demo.rentacarjavafx;

public final class Vehiculo {
    private final String patente;
    private final String marca;

    public Vehiculo(String patente, String marca) {
        if (patente == null || patente.isBlank()) {
            throw new IllegalArgumentException("La patente no puede estar vacía");
        }
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }

        this.patente = patente.trim().toUpperCase();
        this.marca = marca.trim();
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    @Override
    public String toString() {
        return "Vehículo disponible: " + patente + " - Marca: " + marca;
    }
}