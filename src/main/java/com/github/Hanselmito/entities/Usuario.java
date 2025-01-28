package com.github.Hanselmito.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "usuario", schema = "eco")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @ColumnDefault("current_timestamp()")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "idUsuario")
    private List<Habito> habitos = new ArrayList<>();

    @OneToMany(mappedBy = "idUsuario")
    private List<Huella> huellas = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habito> habitos) {
        this.habitos = habitos;
    }

    public List<Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(List<Huella> huellas) {
        this.huellas = huellas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nombre, usuario.nombre) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", fechaRegistro=" + fechaRegistro + '\'' +
                ", Habitos=" + habitos + '\'' +
                ", Huellas=" + huellas + '\'' +
                '}';
    }
}