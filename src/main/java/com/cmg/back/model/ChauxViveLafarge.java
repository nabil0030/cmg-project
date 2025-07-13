package com.cmg.back.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chaux_vive_lafarge")
public class ChauxViveLafarge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @Column(name = "h_entree")
    private String hEntree;

    @Column(name = "h_sortie")
    private String hSortie;

    private String transporteur;

    @Column(name = "numerobl")
    private String numeroBL;

    private String immatricule;
    private double tb;
    private double tare;

    @Column(name = "net_lafarge")
    private double netLafarge;

    private int poste;

    @Column(name = "lieu_chargement")
    private String lieuChargement;

    @Column(name = "lieu_dechargement")
    private String lieuDechargement;

    private String observation;

    public double getNetCMG() {
        return tb - tare;
    }

    public double getEcart() {
        return getNetCMG() - netLafarge;
    }
}
