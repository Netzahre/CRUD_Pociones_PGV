package org.example.objetos;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una poción en el sistema.
 */
@Entity
@Table(name="Pociones")
public class Pociones implements Serializable {
    private static final long serialVersionUID = 8835260018669484706L;
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

    /**
     * Constructor vacío de la clase Pociones.
     */
    public Pociones(){}

    /**
     * Constructor de la clase Pociones.
     * @param nombrePocion Nombre de la poción.
     * @param efectoPocion Efecto de la poción.
     * @param precio Precio de la poción.
     * @param escuela Escuela de la poción.
     * @param tamanio Tamaño de la poción.
     */
    public Pociones(String nombrePocion, String efectoPocion, double precio, Escuela escuela, Tamanio tamanio) {
        this.nombrePocion = nombrePocion;
        this.efectoPocion = efectoPocion;
        this.precio = precio;
        this.escuela = escuela;
        this.tamanio = tamanio;
    }

    /**
     * Método que retorna el id de la poción.
     * @return idPocion Id de la poción.
     */
    public int getIdPocion() {
        return idPocion;
    }

    /**
     * Método que asigna un id a la poción.
     * @param idPocion Id de la poción.
     */
    public void setIdPocion(int idPocion) {
        this.idPocion = idPocion;
    }

    /**
     * Método que retorna el nombre de la poción.
     * @return nombrePocion Nombre de la poción.
     */
    public String getNombrePocion() {
        return nombrePocion;
    }

    /**
     * Método que asigna un nombre a la poción.
     * @param nombrePocion Nombre de la poción.
     */
    public void setNombrePocion(String nombrePocion) {
        this.nombrePocion = nombrePocion;
    }

    /**
     * Método que retorna el efecto de la poción.
     * @return efectoPocion Efecto de la poción.
     */
    public String getEfectoPocion() {
        return efectoPocion;
    }

    /**
     * Método que asigna un efecto a la poción.
     * @param efectoPocion Efecto de la poción.
     */
    public void setEfectoPocion(String efectoPocion) {
        this.efectoPocion = efectoPocion;
    }

    /**
     * Método que retorna el precio de la poción.
     * @return precio Precio de la poción.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Método que asigna un precio a la poción.
     * @param precio Precio de la poción.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Método que retorna la escuela de la poción.
     * @return escuela Escuela de la poción.
     */
    public Escuela getEscuela() {
        return escuela;
    }

    /**
     * Método que asigna una escuela a la poción.
     * @param escuela Escuela de la poción.
     */
    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    /**
     * Método que retorna el tamaño de la poción.
     * @return tamanio Tamaño de la poción.
     */
    public Tamanio getTamanio() {
        return tamanio;
    }

    /**
     * Método que asigna un tamaño a la poción.
     * @param tamanio Tamaño de la poción.
     */
    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
    }

    /**
     * Método que retorna la receta de la poción.
     * @return receta Receta de la poción.
     */
    public List<Recetas> getReceta() {
        return receta;
    }

    /**
     * Método que asigna una receta a la poción.
     * @param receta Receta de la poción.
     */
    public void setReceta(List<Recetas> receta) {
        this.receta = receta;
    }

    /**
     * Método que agrega una receta a la poción.
     * @param receta Receta de la poción.
     */
    public void addReceta(Recetas receta) {
        this.receta.add(receta);
    }

    /**
     * Método que elimina una receta de la poción.
     * @param receta Receta de la poción.
     */
    public void removeReceta(Recetas receta) {
        this.receta.remove(receta);
    }

}
