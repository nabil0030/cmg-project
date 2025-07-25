package com.cmg.back.dto;

import lombok.Data; // Nécessite la dépendance Lombok pour @Data

@Data
public class SyntheseCumulAnnuelGroupesDTO {
    private Double CUMUL_ANNEE_DS_NORD_KA;
    private Double CUMUL_ANNEE_CHAUX_COSUMAR_LAFARGE;
    private Double CUMUL_ANNEE_AUTRES;
}