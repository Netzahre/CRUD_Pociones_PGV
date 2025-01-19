package org.example.objetos;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario", nullable = false)
    private Integer id;

    @Column(name = "nombreUsuario")
    private String nombreUsuario;

    @Column(name = "contrasena")
    private byte[] contrasena;

    @Column(name = "esAdmin")
    private Boolean esAdmin;

    public Usuario(String nombreUsuario, byte[] contrasena, boolean esAdmin) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.esAdmin = esAdmin;
    }

    public Usuario() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public byte[] getContrasena() {
        return contrasena;
    }

    public void setContrasena(byte[] contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

}