package org.example.objetos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que representa la clave primaria compuesta de la tabla Recetas.
 */
@Embeddable
public class RecetasId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "idPocion")
    private int idPocion;

    @Column(name = "IdIngrediente")
    private int IdIngrediente;

    /**
     * Constructor vacío.
     */
    public RecetasId() {}

    /**
     * Constructor con los parámetros necesarios.
     * @param idPocion ID de la poción.
     * @param idIngrediente ID del ingrediente.
     */
    public RecetasId(int idPocion, int idIngrediente) {
        this.idPocion = idPocion;
        this.IdIngrediente = idIngrediente;
    }

    /**
     * Método que compara dos objetos de tipo RecetasId.
     * @param o Objeto a comparar.
     * @return True si son iguales, false si no lo son.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetasId recetasId = (RecetasId) o;
        return idPocion == recetasId.idPocion && IdIngrediente == recetasId.IdIngrediente;
    }

    /**
     * Método que genera un hashcode para un objeto de tipo RecetasId.
     * @return Hashcode del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPocion, IdIngrediente);
    }

    /**
     * Método que devuelve el ID de la poción.
     * @return ID de la poción.
     */
    public int getIdPocion() {
        return idPocion;
    }

    /**
     * Método que establece el ID de la poción.
     * @param idPocion ID de la poción.
     */
    public void setIdPocion(int idPocion) {
        this.idPocion = idPocion;
    }

    /**
     * Método que devuelve el ID del ingrediente.
     * @return ID del ingrediente.
     */
    public int getIdIngrediente() {
        return IdIngrediente;
    }

    /**
     * Método que establece el ID del ingrediente.
     * @param idIngrediente ID del ingrediente.
     */
    public void setIdIngrediente(int idIngrediente) {
        IdIngrediente = idIngrediente;
    }
}
