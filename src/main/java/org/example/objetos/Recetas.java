package org.example.objetos;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Clase que representa la relación muchos a muchos entre Pociones e Ingredientes.
 * Contiene la cantidad de cada ingrediente necesaria para crear una poción.
 */
@Entity
@Table(name="Recetas")
public class Recetas implements Serializable {
    private static final long serialVersionUID = 8835260018669484706L;
    @EmbeddedId
    private RecetasId id;

    @MapsId("idPocion")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPocion", nullable = false)
    private Pociones pocion;

    @MapsId("IdIngrediente")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "IdIngrediente", nullable = false)
    private Ingredientes ingrediente;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;


    /**
     * Constructor vacío
     */
    public Recetas() {}

    /**
     * Constructor con parámetros
     * @param pocion Poción a la que pertenece la receta
     * @param ingrediente Ingrediente necesario para la receta
     * @param cantidad Cantidad de ingrediente necesaria
     */
    public Recetas(Pociones pocion, Ingredientes ingrediente, int cantidad) {
        this.pocion = pocion;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;

        // ¡IMPORTANTE! Crear el id compuesto
        this.id = new RecetasId(pocion.getIdPocion(), ingrediente.getIdIngrediente());
    }

    /** Metodo que devuelve la poción de la receta
     * @return Pociones
     */
    public Pociones getPocion() {
        return pocion;
    }

    /** Metodo que establece la poción de la receta
     * @param pocion Poción
     */
    public void setPocion(Pociones pocion) {
        this.pocion = pocion;
    }

    /** Metodo que devuelve el ingrediente de la receta
     * @return Ingredientes
     */
    public Ingredientes getIngrediente() {
        return ingrediente;
    }

    /** Metodo que establece el ingrediente de la receta
     * @param ingrediente Ingrediente
     */
    public void setIngrediente(Ingredientes ingrediente) {
        this.ingrediente = ingrediente;
    }

    /** Metodo que devuelve la cantidad de ingrediente de la receta
     * @return int
     */
    public int getCantidad() {
        return cantidad;
    }

    /** Metodo que establece la cantidad de ingrediente de la receta
     * @param cantidad int
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /** Metodo que devuelve el id compuesto de la receta
     * @return RecetasId
     */
    public RecetasId getId() {
        return id;
    }

    /** Metodo que establece el id compuesto de la receta
     * @param id RecetasId
     */
    public void setId(RecetasId id) {
        this.id = id;
    }
}
