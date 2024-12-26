package org.example.objetos;

import jakarta.persistence.*;

@Entity
@Table(name="Recetas")
public class Recetas {
    @EmbeddedId
    private RecetasId id;

    @MapsId("idPocion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPocion", nullable = false)
    private Pociones pocion;

    @MapsId("IdIngrediente")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdIngrediente", nullable = false)
    private Ingredientes ingrediente;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;


    // Constructor sin parámetros, necesario para Hibernate
    public Recetas() {}

    // Constructor con parámetros
    public Recetas(Pociones pocion, Ingredientes ingrediente, int cantidad) {
        this.pocion = pocion;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
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
}
