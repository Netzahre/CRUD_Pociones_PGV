package org.example.objetos;

public class Ingredientes {
    private int idIngrediente;
    private String nombreIngrediente;
    private TiposIngrediente tipoIngrediente;

    public Ingredientes(int idIngrediente, String nombreIngrediente, TiposIngrediente tipoIngrediente) {
        this.idIngrediente = idIngrediente;
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

    public enum TiposIngrediente{VEGETAL,MINERAL,ORGANICO,MAGICO}
}
