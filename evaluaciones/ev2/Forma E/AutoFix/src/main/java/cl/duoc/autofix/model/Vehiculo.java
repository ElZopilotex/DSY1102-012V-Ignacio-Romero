package cl.duoc.autofix.model;

/**
 * Representa a cualquier vehículo recibido en el Taller Mecánico AutoFix.
 * Define los datos comunes y delega en cada tipo de vehículo el cálculo del
 * costo del servicio.
 */
public abstract class Vehiculo {

    private String marca;
    private int anioFabricacion;
    private int kilometraje;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    protected Vehiculo() {
    }

    public Vehiculo(String marca, int anioFabricacion, int kilometraje) {
        setMarca(marca);
        setAnioFabricacion(anioFabricacion);
        setKilometraje(kilometraje);
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede ser nula ni vacía.");
        }
        this.marca = marca.trim();
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        if (anioFabricacion < 1990 || anioFabricacion > 2026) {
            throw new IllegalArgumentException("El año de fabricación debe estar entre 1990 y 2026.");
        }
        this.anioFabricacion = anioFabricacion;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        if (kilometraje <= 0) {
            throw new IllegalArgumentException("El kilometraje debe ser mayor que cero.");
        }
        this.kilometraje = kilometraje;
    }

    /**
     * Calcula el costo del servicio según las reglas propias de cada tipo de vehículo.
     *
     * @return costo del servicio en pesos
     */
    public abstract double calcularCostoServicio();

    /**
     * Retorna el nombre del tipo de vehículo (por ejemplo, "Auto" o "Furgón").
     *
     * @return tipo de vehículo
     */
    public abstract String getTipo();

    @Override
    public String toString() {
        return "Marca: " + marca + " | Año: " + anioFabricacion;
    }
}
