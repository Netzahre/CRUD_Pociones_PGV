package org.example.objetos;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public enum TiposIngrediente {VEGETAL, MINERAL, ORGANICO, MAGICO}


}
