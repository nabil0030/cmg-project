package com.cmg.back.dto;

public class RapportHajjarDto {

    private String section;
    private String designation;
    private String poste;
    private Double valeur;
    private Double totalJour;
    private Double totalMois;
    private Double totalAnnee;

    public RapportHajjarDto() {}

    public RapportHajjarDto(String section, String designation, String poste,
                            Double valeur, Double totalJour, Double totalMois, Double totalAnnee) {
        this.section = section;
        this.designation = designation;
        this.poste = poste;
        this.valeur = valeur;
        this.totalJour = totalJour;
        this.totalMois = totalMois;
        this.totalAnnee = totalAnnee;
    }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public Double getValeur() { return valeur; }
    public void setValeur(Double valeur) { this.valeur = valeur; }

    public Double getTotalJour() { return totalJour; }
    public void setTotalJour(Double totalJour) { this.totalJour = totalJour; }

    public Double getTotalMois() { return totalMois; }
    public void setTotalMois(Double totalMois) { this.totalMois = totalMois; }

    public Double getTotalAnnee() { return totalAnnee; }
    public void setTotalAnnee(Double totalAnnee) { this.totalAnnee = totalAnnee; }
}
