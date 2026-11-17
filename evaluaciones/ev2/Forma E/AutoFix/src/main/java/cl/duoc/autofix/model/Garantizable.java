package cl.duoc.autofix.model;

/**
 * Contrato que cumplen los vehículos que participan en el proceso de
 * garantía del taller.
 */
public interface Garantizable {

    /**
     * Indica si el vehículo tiene una garantía activa.
     *
     * @return true si la garantía está activa
     */
    boolean tieneGarantiaActiva();

    /**
     * Registra que el vehículo activa su garantía.
     */
    void activarGarantia();
}
