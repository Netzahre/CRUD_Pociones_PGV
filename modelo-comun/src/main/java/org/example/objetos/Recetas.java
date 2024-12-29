package org.example.objetos;


import java.io.Serializable;

public class Recetas implements Serializable {
    private static final long serialVersionUID = 8835260018669484706L;


    private RecetasId id;

    private Pociones pocion;

    private Ingredientes ingrediente;

    private int cantidad;


    // Constructor sin parámetros, necesario para Hibernate
    public Recetas() {}

    // Constructor con parámetros
    public Recetas(Pociones pocion, Ingredientes ingrediente, int cantidad) {

        this.pocion = pocion;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        // Inicializa el ID compuesto
        this.id = new RecetasId(pocion.getIdPocion(), ingrediente.getIdIngrediente());
    }

    public Pociones getPocion() {
        return pocion;
    }

    public void setPocion(Pociones pocion) {
        this.pocion = pocion;
    }

    public Ingredientes getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingredientes ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public RecetasId getId() {
        return id;
    }

    public void setId(RecetasId id) {
        this.id = id;
    }
}
