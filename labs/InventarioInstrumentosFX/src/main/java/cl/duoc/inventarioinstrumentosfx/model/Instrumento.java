package cl.duoc.inventarioinstrumentosfx.model;

public class Instrumento {
    private String codigo;
    private String nombre;
    private String categoria;

    public Instrumento() {
        // Jackson necesita construir el objeto al leer JSON.
    }

    public Instrumento(String codigo, String nombre, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}