package org.example.ejercicio_semana_9;

public class Mascota {
    private String nombre;
    private int edad;
    private String categoria;

    public Mascota(String nombre, int edad, String categoria) {
        this.nombre = nombre;
        this.edad = edad;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString(){
        return "Mascota: " + nombre + ", edad: " + edad + ", categoria: " + categoria;
    }
}

