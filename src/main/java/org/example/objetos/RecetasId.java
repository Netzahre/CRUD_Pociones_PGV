package org.example.objetos;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class RecetasId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "idPocion")
    private int idPocion;

    @Column(name = "IdIngrediente")
    private int IdIngrediente;


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
}
