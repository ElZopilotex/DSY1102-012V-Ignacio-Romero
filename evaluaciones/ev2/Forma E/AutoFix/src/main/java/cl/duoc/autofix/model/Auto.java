package cl.duoc.autofix.model;

/**
 * Auto atendido en el taller. Participa en el proceso de garantía.
 * Costo del servicio: base $25.000; si no cuenta con garantía de fábrica vigente, recargo del 30%.
 */
public class Auto extends Vehiculo implements Garantizable {

    private static final double COSTO_BASE = 25000;
    private static final double RECARGO_SIN_GARANTIA_FABRICA = 0.30;

    private String modelo;
    private boolean garantiaVigente;
    private boolean garantiaActiva;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    public Auto() {
    }

    public Auto(String marca, int anioFabricacion, int kilometraje, String modelo, boolean garantiaVigente) {
        super(marca, anioFabricacion, kilometraje);
        setModelo(modelo);
        setGarantiaVigente(garantiaVigente);
        this.garantiaActiva = false;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isGarantiaVigente() {
        return garantiaVigente;
    }

    public void setGarantiaVigente(boolean garantiaVigente) {
        this.garantiaVigente = garantiaVigente;
    }

    @Override
    public boolean tieneGarantiaActiva() {
        return garantiaActiva;
    }

    @Override
    public void activarGarantia() {
        this.garantiaActiva = true;
    }

    @Override
    public double calcularCostoServicio() {
        if (garantiaVigente) {
            return COSTO_BASE;
        }
        return COSTO_BASE + COSTO_BASE * RECARGO_SIN_GARANTIA_FABRICA;
    }

    @Override
    public String getTipo() {
        return "Auto";
    }
}
