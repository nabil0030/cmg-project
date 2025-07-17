package com.cmg.back.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Data
public class SyntheseDto {
    private LocalDate date;
    private YearMonth mois;
    private Integer annee;

    private double dsClassique;
    private double dsNord;
    private double chauxViveLafarge;
    private double cbulkArgentifere;
    private double chauxVive;
    private double controleBasculeHJDS;
    private double controleBasculeKA;
    private double expCuivreNord;
    private double expCuivreOncf;
    private double expPbCmgOnf;
    private double expZincOncf;
    private double expZincSafi;

    private BigDecimal global;
}
