package com.github.Hanselmito.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "actividad", schema = "eco")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private com.github.Hanselmito.entities.Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<com.github.Hanselmito.entities.Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
    private Set<com.github.Hanselmito.entities.Huella> huellas = new LinkedHashSet<>();

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

    public com.github.Hanselmito.entities.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(com.github.Hanselmito.entities.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Set<com.github.Hanselmito.entities.Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<com.github.Hanselmito.entities.Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<com.github.Hanselmito.entities.Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<com.github.Hanselmito.entities.Huella> huellas) {
        this.huellas = huellas;
    }

}