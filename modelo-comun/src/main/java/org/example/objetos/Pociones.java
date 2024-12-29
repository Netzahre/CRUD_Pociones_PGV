package org.example.objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pociones implements Serializable {
    private static final long serialVersionUID = 8835260018669484706L;
    private int idPocion;
    private String nombrePocion;
    private String efectoPocion;
    private double precio;
    private Escuela escuela;
    private Tamanio tamanio;
    private List<Ingredientes> ingredientes = new ArrayList<>();

    public enum Escuela {CONJURACION, EVOCACION, ILUSION, NIGROMANCIA, TRANSMUTACION, ABJURACION, ENCANTAMIENTO, DIVINACION, UNIVERSAL}
    public enum Tamanio {PEQUEÑO, MEDIANO, GRANDE}
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

}
