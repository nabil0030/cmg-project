package com.cmg.back.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lieux_chargement")
public class LieuChargement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    public LieuChargement() {}

    public LieuChargement(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
