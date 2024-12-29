package org.example.objetos;


import java.io.Serializable;
import java.util.Objects;

public class RecetasId implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPocion;


    private int IdIngrediente;

    public RecetasId() {}

    public RecetasId(int idPocion, int idIngrediente) {
        this.idPocion = idPocion;
        this.IdIngrediente = idIngrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecetasId recetasId = (RecetasId) o;
        return idPocion == recetasId.idPocion && IdIngrediente == recetasId.IdIngrediente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPocion, IdIngrediente);
    }

    public int getIdPocion() {
        return idPocion;
    }

    public void setIdPocion(int idPocion) {
        this.idPocion = idPocion;
    }

    public int getIdIngrediente() {
        return IdIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        IdIngrediente = idIngrediente;
    }
}
