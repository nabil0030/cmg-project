package com.cmg.back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ControleBasculeKA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String hEntree;
    private String hSortie;
    private String transporteur;
    private String numeroBL;
    private String immatricule;
    private Double tb;
    private Double tare;
    private Double net;
    private Integer poste;
    private String lieuDeDecharge;

    @PrePersist
    @PreUpdate
    public void calculerNet() {
        if (tb != null && tare != null) {
            this.net = tb - tare;
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void sethEntree(String hEntree) {
        this.hEntree = hEntree;
    }

    public void sethSortie(String hSortie) {
        this.hSortie = hSortie;
    }

    public void setTransporteur(String transporteur) {
        this.transporteur = transporteur;
    }

    public void setNumeroBL(String numeroBL) {
        this.numeroBL = numeroBL;
    }

    public void setImmatricule(String immatricule) {
        this.immatricule = immatricule;
    }

    public void setTb(Double tb) {
        this.tb = tb;
    }

    public void setTare(Double tare) {
        this.tare = tare;
    }

    public void setNet(Double net) {
        this.net = net;
    }

    public void setPoste(Integer poste) {
        this.poste = poste;
    }

    public void setLieuDeDecharge(String lieuDeDecharge) {
        this.lieuDeDecharge = lieuDeDecharge;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String gethEntree() {
        return hEntree;
    }

    public String gethSortie() {
        return hSortie;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public String getNumeroBL() {
        return numeroBL;
    }

    public String getImmatricule() {
        return immatricule;
    }

    public Double getTb() {
        return tb;
    }

    public Double getTare() {
        return tare;
    }

    public Double getNet() {
        return net;
    }

    public Integer getPoste() {
        return poste;
    }

    public String getLieuDeDecharge() {
        return lieuDeDecharge;
    }

    public double getHEntree() {
        return 0;
    }

    public double getHSortie() {
        return 0;
    }
}
