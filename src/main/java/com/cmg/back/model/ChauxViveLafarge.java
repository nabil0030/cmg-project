package com.cmg.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChauxViveLafarge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;// ✅ BON TYPE pour Hibernate + requêtes


    @DateTimeFormat(pattern = "HH:mm")
    private String heureEntree;

    @DateTimeFormat(pattern = "HH:mm")
    private String heureSortie;

    private String transporteur;
    private String numeroBL;
    private String immatricule;

    private Double tb;
    private Double tare;

    private Double netCmg;       // Calculé automatiquement
    private Double netLafarge;   // Donné
    private Double ecart;        // netCmg - netLafarge

    private Integer poste;

    private String lieuChargement;
    private String lieuDechargement;

    private String observation;


}
