package org.example.objetos;


import jakarta.persistence.*;

@Entity
@Table(name = "Ingredientes")
public class Ingredientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdIngrediente")
    private int idIngrediente;
    @Column (name = "nombreIngrediente", nullable = false )
    private String nombreIngrediente;
    @Enumerated(EnumType.STRING)
    @Column (name = "tipoIngrediente", nullable = false)
    private TiposIngrediente tipoIngrediente;

    public Ingredientes(){}

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

    public enum TiposIngrediente{VEGETAL,MINERAL,ORGANICO,MAGICO}
}
