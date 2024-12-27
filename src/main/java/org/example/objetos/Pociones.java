package org.example.objetos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Pociones")
public class Pociones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPocion")
    private int idPocion;

    @Column(name="nombrePocion",nullable = false)
    private String nombrePocion;

    @Column(name="efectoPocion",nullable = false)
    private String efectoPocion;

    public enum Escuela {CONJURACION, EVOCACION, ILUSION, NIGROMANCIA, TRANSMUTACION, ABJURACION, ENCANTAMIENTO, DIVINACION, UNIVERSAL}
    public enum Tamanio {PEQUEÑO,MEDIANO,GRANDE}

    @Column (name="precio",nullable = false)
    private double precio;

    @Enumerated(EnumType.STRING)
    @Column (name="escuela",nullable = false)
    private Escuela escuela;

    @Enumerated(EnumType.STRING)
    @Column (name="tamanio",nullable = false)
    private Tamanio tamanio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pocion", fetch = FetchType.EAGER)
    List<Recetas> receta = new ArrayList<>();



    public Pociones(){}

    public Pociones(String nombrePocion, String efectoPocion, double precio, Escuela escuela, Tamanio tamanio) {
        this.nombrePocion = nombrePocion;
        this.efectoPocion = efectoPocion;
        this.precio = precio;
        this.escuela = escuela;
        this.tamanio = tamanio;
    }

    public int getIdPocion() {
        return idPocion;
    }

    public void setIdPocion(int idPocion) {
        this.idPocion = idPocion;
    }

    public String getNombrePocion() {
        return nombrePocion;
    }

    public void setNombrePocion(String nombrePocion) {
        this.nombrePocion = nombrePocion;
    }

    public String getEfectoPocion() {
        return efectoPocion;
    }

    public void setEfectoPocion(String efectoPocion) {
        this.efectoPocion = efectoPocion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
    }

    public List<Recetas> getReceta() {
        return receta;
    }

    public void setReceta(List<Recetas> receta) {
        this.receta = receta;
    }

    public void addReceta(Recetas receta) {
        this.receta.add(receta);
    }

    public void removeReceta(Recetas receta) {
        this.receta.remove(receta);
    }

    public String toStringo(){
        return(nombrePocion +" - "+efectoPocion);
    }



}
