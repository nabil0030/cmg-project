package com.cmg.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "chaux_vive")
public class ChauxVive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime hEntree;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime hSortie;

    private String transporteur;
    private String numeroBL;
    private String immat;
    private Double tb;
    private Double tare;
    private Double netCmg; // auto-calculé
    private Integer poste;
    private Double netCosumar;
    private Double ecart; // auto-calculé
    private String lieuChargement;
    private String lieuDechargement;
    private String observation;

    @PrePersist @PreUpdate
    public void calculsAuto() {
        this.netCmg = (tb != null && tare != null) ? tb - tare : 0.0;
        if (netCosumar != null && netCmg != null)
            this.ecart = netCmg - netCosumar;
        else
            this.ecart = 0.0;
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

    public String getImmat() {
        return immat;
    }

    public Double getTb() {
        return tb;
    }

    public Double getTare() {
        return tare;
    }

    public Double getNetCmg() {
        return netCmg;
    }

    public Integer getPoste() {
        return poste;
    }

    public Double getNetCosumar() {
        return netCosumar;
    }

    public Double getEcart() {
        return ecart;
    }

    public String getLieuChargement() {
        return lieuChargement;
    }

    public String getLieuDechargement() {
        return lieuDechargement;
    }

    public String getObservation() {
        return observation;
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

    public void setImmat(String immat) {
        this.immat = immat;
    }

    public void setTb(Double tb) {
        this.tb = tb;
    }

    public void setTare(Double tare) {
        this.tare = tare;
    }

    public void setNetCmg(Double netCmg) {
        this.netCmg = netCmg;
    }

    public void setPoste(Integer poste) {
        this.poste = poste;
    }

    public void setNetCosumar(Double netCosumar) {
        this.netCosumar = netCosumar;
    }

    public void setEcart(Double ecart) {
        this.ecart = ecart;
    }

    public void setLieuChargement(String lieuChargement) {
        this.lieuChargement = lieuChargement;
    }

    public void setLieuDechargement(String lieuDechargement) {
        this.lieuDechargement = lieuDechargement;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

// Génère getters/setters via ton IDE
}
