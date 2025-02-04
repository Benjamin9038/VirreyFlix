package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime fecha_reproduccion;

    @ManyToOne
    @JoinColumn(name = "perfil_id") // Corregido
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "episodio_id") // Corregido
    private Episodio episodio;

    public Historial() {
    }

    public Historial(LocalDateTime fecha_reproduccion) {
        this.fecha_reproduccion = fecha_reproduccion;
        this.perfil = perfil;
        this.episodio = episodio; // Corregido
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFecha_reproduccion() {
        return fecha_reproduccion;
    }

    public void setFecha_reproduccion(LocalDateTime fecha_reproduccion) {
        this.fecha_reproduccion = fecha_reproduccion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Episodio getEpisodio() {
        return episodio;
    }

    public void setEpisodio(Episodio episodio) {
        this.episodio = episodio;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "id=" + id +
                ", fecha_reproduccion=" + fecha_reproduccion +
                ", perfil=" + perfil +
                ", episodio=" + episodio +
                '}';
    }
}
