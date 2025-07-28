package com.cmg.back.model;

import jakarta.persistence.*;

@Entity
@Table(name = "postes")
public class Poste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    public Poste() {}
    public Poste(String nom) { this.nom = nom; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
