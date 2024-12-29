package org.example.objetos;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Ingredientes implements Serializable {
    private static final long serialVersionUID = 8835260018669484706L;

    private int idIngrediente;

    private String nombreIngrediente;

    private TiposIngrediente tipoIngrediente;

    List<Recetas> receta = new ArrayList<>();

    public Ingredientes() {
    }

    public Ingredientes(String nombreIngrediente, TiposIngrediente tipoIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
        this.tipoIngrediente = tipoIngrediente;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public TiposIngrediente getTipoIngrediente() {
        return tipoIngrediente;
    }

    public void setTipoIngrediente(TiposIngrediente tipoIngrediente) {
        this.tipoIngrediente = tipoIngrediente;
    }

    public enum TiposIngrediente {VEGETAL, MINERAL, ORGANICO, MAGICO}


}
