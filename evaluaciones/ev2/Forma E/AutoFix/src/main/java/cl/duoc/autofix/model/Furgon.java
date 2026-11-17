package cl.duoc.autofix.model;

/**
 * Furgón atendido en el taller.
 * Costo del servicio: base $35.000; si la capacidad de carga supera 1,5 toneladas, recargo del 25%.
 */
public class Furgon extends Vehiculo {

    private static final double COSTO_BASE = 35000;
    private static final double RECARGO_CAPACIDAD = 0.25;
    private static final double LIMITE_TONELADAS = 1.5;

    private double capacidadToneladas;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    public Furgon() {
    }

    public Furgon(String marca, int anioFabricacion, int kilometraje, double capacidadToneladas) {
        super(marca, anioFabricacion, kilometraje);
        setCapacidadToneladas(capacidadToneladas);
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    public void setCapacidadToneladas(double capacidadToneladas) {
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularCostoServicio() {
        if (capacidadToneladas > LIMITE_TONELADAS) {
            return COSTO_BASE + COSTO_BASE * RECARGO_CAPACIDAD;
        }
        return COSTO_BASE;
    }

    @Override
    public String getTipo() {
        return "Furgón";
    }
}
