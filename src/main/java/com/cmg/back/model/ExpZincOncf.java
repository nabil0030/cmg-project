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
public class ExpZincOncf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")


    private String heureEntree;
    private String heureSortie;


    private String transporteur;
    private String numeroBL;
    private String matricule;

    private double tb;
    private double tare;
    private double tnH; // calculé automatiquement dans le contrôleur

    private String numeroContenaire;
    private int poste;
    private String lieuDeDechargement;
    private String observation;


}
