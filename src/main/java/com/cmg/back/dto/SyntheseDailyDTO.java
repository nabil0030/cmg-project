package com.cmg.back.dto;

import lombok.Data; // Nécessite la dépendance Lombok pour @Data

@Data
public class SyntheseDailyDTO {
    // LES ENTREES
    private Double dsClassique;
    private Double dsNord;
    private Double controleBasculeKA;
    private Double chauxVive;
    private Double chauxViveLafarge;

    // LES EXPEDITION
    private Double expCuivreOncf;
    private Double expCuivreNord;
    private Double expPbCmgOnf;
    private Double cbulkArgentifere;
    private Double expZincOncf;
    private Double expZincSafi; // ZN CBULK

    public void setControleBasculeHJDS(Double aDouble) {
    }

    // Note: ZN PORT EN VRAC n'est pas inclus ici car il est géré comme 0.0 dans le frontend/service
    // Si vous aviez des données pour cela, vous ajouteriez une propriété ici.
}
