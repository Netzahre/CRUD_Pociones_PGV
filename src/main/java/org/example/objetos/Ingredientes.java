package org.example.objetos;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un ingrediente de una poción.
 */
@Entity
@Table(name = "Ingredientes")
public class Ingredientes implements Serializable {
    private static final long serialVersionUID = 8835260018669484706L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIngrediente")
    private int idIngrediente;
    @Column(name = "nombreIngrediente", nullable = false)
    private String nombreIngrediente;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoIngrediente", nullable = false)
    private TiposIngrediente tipoIngrediente;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ingrediente", fetch = FetchType.EAGER)
    private transient List<Recetas> receta = new ArrayList<>();

    /**
     * Constructor vacío.
     */
    public Ingredientes() {
    }

    /**
     * Constructor con los parámetros necesarios para crear un ingrediente.
     *
     * @param nombreIngrediente Nombre del ingrediente.
     * @param tipoIngrediente   Tipo del ingrediente.
     */
    public Ingredientes(String nombreIngrediente, TiposIngrediente tipoIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
        this.tipoIngrediente = tipoIngrediente;
    }

    /**
     * Obtiene el id de ingrediente.
     *
     * @return id de ingrediente.
     */
    public int getIdIngrediente() {
        return idIngrediente;
    }

    /** Establece el id del ingrediente.
     * @param idIngrediente Id del ingrediente.
     */
    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /**
     * Obtiene el nombre de ingrediente.
     *
     * @return nombre de ingrediente.
     */
    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    /**
     * Establece el nombre de ingrediente.
     *
     * @param nombreIngrediente nombre de ingrediente.
     */
    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    /**
     * Obtiene el tipo de ingrediente.
     *
     * @return tipo de ingrediente.
     */
    public TiposIngrediente getTipoIngrediente() {
        return tipoIngrediente;
    }

    /**
     * Establece el tipo de ingrediente.
     *
     * @param tipoIngrediente tipo de ingrediente.
     */
    public void setTipoIngrediente(TiposIngrediente tipoIngrediente) {
        this.tipoIngrediente = tipoIngrediente;
    }

    /**
     * Enum que representa los tipos de ingredientes.
     */
    public enum TiposIngrediente {VEGETAL, MINERAL, ORGANICO, MAGICO}

}
