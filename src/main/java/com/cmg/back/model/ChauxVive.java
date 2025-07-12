package com.cmg.back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChauxVive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hEntree;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hSortie;


    private String transporteur;
    private String numeroBL;
    private String immatricule;

    private Double tb;
    private Double tare;
    private Double netCmg;      // = tb - tare

    private Integer poste;
    private Double netCosumar;
    private Double ecart;       // = netCmg - netCosumar

    private String lieuChargement;
    private String lieuDechargement;
    private String observation;

    // Setters personnalisés pour assurer le recalcul automatique
    public void setTb(Double tb) {
        this.tb = tb;
        recalculateNetCmgAndEcart();
    }

    public void setTare(Double tare) {
        this.tare = tare;
        recalculateNetCmgAndEcart();
    }

    public void setNetCosumar(Double netCosumar) {
        this.netCosumar = netCosumar;
        recalculateEcart();
    }

    private void recalculateNetCmgAndEcart() {
        if (tb != null && tare != null) {
            this.netCmg = tb - tare;
            recalculateEcart();
        }
    }

    private void recalculateEcart() {
        if (netCmg != null && netCosumar != null) {
            this.ecart = netCmg - netCosumar;
        }
    }
}
