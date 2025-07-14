package com.cmg.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "exp_zinc_safi")
public class ExpZincSafi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "h_entree")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hEntree;

    @Column(name = "h_sortie")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hSortie;

    private String transporteur;
    private String numeroBL;
    private String matricule;
    private Double tb;
    private Double tare;
    private Double tnH; // calculé automatiquement
    private Integer poste;
    private String lieuDeDechargement;
    private String observation;

    @PrePersist @PreUpdate
    public void calculerTnH() {
        if (tb != null && tare != null) tnH = tb - tare;
        else tnH = 0.0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void sethEntree(LocalTime hEntree) {
        this.hEntree = hEntree;
    }

    public void sethSortie(LocalTime hSortie) {
        this.hSortie = hSortie;
    }

    public void setTransporteur(String transporteur) {
        this.transporteur = transporteur;
    }

    public void setNumeroBL(String numeroBL) {
        this.numeroBL = numeroBL;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setTb(Double tb) {
        this.tb = tb;
    }

    public void setTare(Double tare) {
        this.tare = tare;
    }

    public void setTnH(Double tnH) {
        this.tnH = tnH;
    }

    public void setPoste(Integer poste) {
        this.poste = poste;
    }

    public void setLieuDeDechargement(String lieuDeDechargement) {
        this.lieuDeDechargement = lieuDeDechargement;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHEntree() {
        return hEntree;
    }

    public LocalTime getHSortie() {
        return hSortie;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public String getNumeroBL() {
        return numeroBL;
    }

    public String getMatricule() {
        return matricule;
    }

    public Double getTb() {
        return tb;
    }

    public Double getTare() {
        return tare;
    }

    public Double getTnH() {
        return tnH;
    }

    public Integer getPoste() {
        return poste;
    }

    public String getLieuDeDechargement() {
        return lieuDeDechargement;
    }

    public String getObservation() {
        return observation;
    }
}
