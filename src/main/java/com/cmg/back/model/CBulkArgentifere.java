package com.cmg.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CBulkArgentifere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String hEntree;
    private String hSortie;
    private String transporteur;
    private String numeroBL;
    private String matricule;
    private double tb;
    private double tare;
    private double tnH;
    private String numeroContenaire;
    private int poste;
    private String lieuDeDechargement;
    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
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

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setTb(double tb) {
        this.tb = tb;
    }

    public void setTare(double tare) {
        this.tare = tare;
    }

    public void setTnH(double tnH) {
        this.tnH = tnH;
    }

    public void setNumeroContenaire(String numeroContenaire) {
        this.numeroContenaire = numeroContenaire;
    }

    public void setPoste(int poste) {
        this.poste = poste;
    }

    public void setLieuDeDechargement(String lieuDeDechargement) {
        this.lieuDeDechargement = lieuDeDechargement;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public String getMatricule() {
        return matricule;
    }

    public double getTb() {
        return tb;
    }

    public double getTare() {
        return tare;
    }

    public double getTnH() {
        return tnH;
    }

    public String getNumeroContenaire() {
        return numeroContenaire;
    }

    public int getPoste() {
        return poste;
    }

    public String getLieuDeDechargement() {
        return lieuDeDechargement;
    }

    public String getObservation() {
        return observation;
    }
}
