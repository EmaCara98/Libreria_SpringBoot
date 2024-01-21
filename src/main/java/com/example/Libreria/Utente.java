package com.example.Libreria;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "utenti_libri",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private Set<Libro> libri = new HashSet<>();

    public Utente(Long id, String nome, String cognome, String username, String password, Set<Libro> libri) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.libri = libri;
    }

    public Utente() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Libro> getLibri() {
        return libri;
    }

    public void setLibri(Set<Libro> libri) {
        this.libri = libri;
    }
}
