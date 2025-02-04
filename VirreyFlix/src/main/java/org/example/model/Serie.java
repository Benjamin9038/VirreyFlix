package org.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200)
    private String titulo;

    @Column(length = 100)
    private String genero;

    private int calificacion_edad;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Episodio> episodios = new HashSet<>();

    public Serie() {
    }

    public Serie(String titulo, String genero, int calificacion_edad) {
        this.titulo = titulo;
        this.genero = genero;
        this.calificacion_edad = calificacion_edad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCalificacion_edad() {
        return calificacion_edad;
    }

    public void setCalificacion_edad(int calificacion_edad) {
        this.calificacion_edad = calificacion_edad;
    }

    public Set<Episodio> getEpisodios() {
        return episodios;
    }


    public void setEpisodios(Set<Episodio> episodios) {
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", calificacion_edad=" + calificacion_edad +
                '}';
    }
}
