package com.cmg.back.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class DsNordRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime hEntree;
    private LocalTime hSortie;
    private String transporteur;
    private String numeroBL;
    private String immatricule;
    private Double tb;
    private Double tare;
    private Double net;
    private Integer poste;
    private String lieuDeDecharge;
    private String observation;

    @PrePersist
    @PreUpdate
    public void calculNet() {
        if (tb != null && tare != null) {
            net = tb - tare;
        }
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

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime gethEntree() {
        return hEntree;
    }

    public LocalTime gethSortie() {
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

    public String getObservation() {
        return observation;
    }
}
