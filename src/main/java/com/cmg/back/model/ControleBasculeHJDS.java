package com.cmg.back.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ControleBasculeHJDS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String transporteur;
    private String numeroBL;
    private String immatricule;
    private Double netDS;
    private Double netCMG;
    private Double ecart;
    private String observation;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getTransporteur() { return transporteur; }
    public void setTransporteur(String transporteur) { this.transporteur = transporteur; }

    public String getNumeroBL() { return numeroBL; }
    public void setNumeroBL(String numeroBL) { this.numeroBL = numeroBL; }

    public String getImmatricule() { return immatricule; }
    public void setImmatricule(String immatricule) { this.immatricule = immatricule; }

    public Double getNetDS() { return netDS; }
    public void setNetDS(Double netDS) { this.netDS = netDS; }

    public Double getNetCMG() { return netCMG; }
    public void setNetCMG(Double netCMG) { this.netCMG = netCMG; }

    public Double getEcart() { return ecart; }
    public void setEcart(Double ecart) { this.ecart = ecart; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
}
