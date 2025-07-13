package com.cmg.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ds_nord")
public class DsNordRecord {

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
    private String immatricule;
    private Double tb;
    private Double tare;
    private Double net;
    private Integer poste;
    private String lieuDeDecharge;
    private String observation;

    @PrePersist
    @PreUpdate
    private void calculNet() {
        if (tb != null && tare != null) {
            net = tb - tare;
        }
    }

    // ─── SETTERS ─────────────────────────────────────────

    public void setId(Long id) { this.id = id; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setHEntree(LocalTime hEntree) { this.hEntree = hEntree; }
    public void setHSortie(LocalTime hSortie) { this.hSortie = hSortie; }
    public void setTransporteur(String transporteur) { this.transporteur = transporteur; }
    public void setNumeroBL(String numeroBL) { this.numeroBL = numeroBL; }
    public void setImmatricule(String immatricule) { this.immatricule = immatricule; }
    public void setTb(Double tb) { this.tb = tb; }
    public void setTare(Double tare) { this.tare = tare; }
    public void setNet(Double net) { this.net = net; }
    public void setPoste(Integer poste) { this.poste = poste; }
    public void setLieuDeDecharge(String lieuDeDecharge) { this.lieuDeDecharge = lieuDeDecharge; }
    public void setObservation(String observation) { this.observation = observation; }

    // ─── GETTERS ─────────────────────────────────────────

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public LocalTime getHEntree() { return hEntree; }
    public LocalTime getHSortie() { return hSortie; }
    public String getTransporteur() { return transporteur; }
    public String getNumeroBL() { return numeroBL; }
    public String getImmatricule() { return immatricule; }
    public Double getTb() { return tb; }
    public Double getTare() { return tare; }
    public Double getNet() { return net; }
    public Integer getPoste() { return poste; }
    public String getLieuDeDecharge() { return lieuDeDecharge; }
    public String getObservation() { return observation; }
}
