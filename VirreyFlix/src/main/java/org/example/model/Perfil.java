package org.example.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String nombre;

    private int edad;

    @OneToOne(cascade = CascadeType.ALL)
    Usuario usuario;

    @OneToMany(mappedBy = "perfil",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Historial> historial=new HashSet<>();

    public Perfil() {
    }

    public Perfil(String nombre, int edad, Usuario usuario) {
        this.nombre = nombre;
        this.edad = edad;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", usuario=" + usuario +
                '}';
    }
}
