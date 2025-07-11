package com.cmg.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChauxVive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String hEntree;
    private String hSortie;
    private String transporteur;
    private String numeroBL;
    private String immatricule;
    private double tb;
    private double tare;

    public double getNetCMG() {
        return tb - tare;
    }

    private int poste;
    private double netCosumar;
    private double ecart;
    private String lieuChargement;
    private String lieuDechargement;
    private String observation;

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

    public void setImmatricule(String immatricule) {
        this.immatricule = immatricule;
    }

    public void setTb(double tb) {
        this.tb = tb;
    }

    public void setTare(double tare) {
        this.tare = tare;
    }

    public void setPoste(int poste) {
        this.poste = poste;
    }

    public void setNetCosumar(double netCosumar) {
        this.netCosumar = netCosumar;
    }

    public void setEcart(double ecart) {
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

    public double getTb() {
        return tb;
    }

    public double getTare() {
        return tare;
    }

    public int getPoste() {
        return poste;
    }

    public double getNetCosumar() {
        return netCosumar;
    }

    public double getEcart() {
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

    public double getHEntree() {
        return 0;
    }

    public double getHSortie() {
        return 0;
    }
}
