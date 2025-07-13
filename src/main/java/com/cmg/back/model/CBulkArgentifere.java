package com.cmg.back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CBulkArgentifere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @DateTimeFormat(pattern = "HH:mm")
    private String heureEntree;

    @DateTimeFormat(pattern = "HH:mm")
    private String heureSortie;

    private String transporteur;
    private String numeroBL;
    private String matricule;

    private Double tb;
    private Double tare;
    private Double tnh; // Calculé automatiquement (tb - tare)

    private String numeroContenaire;
    private Integer poste;

    private String lieuDechargement;
    private String observation;
}
