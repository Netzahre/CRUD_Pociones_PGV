package org.example.objetos;

public class Recetas {
    private int idPocion;
    private int idIngrediente;
    private int cantidad;

    public Recetas(int idPocion, int idIngrediente, int cantidad) {
        this.idPocion = idPocion;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    public int getIdPocion() {
        return idPocion;
    }

    public void setIdPocion(int idPocion) {
        this.idPocion = idPocion;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
