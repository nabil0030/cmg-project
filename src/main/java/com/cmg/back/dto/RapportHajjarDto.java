package com.cmg.back.dto;

public class RapportHajjarDto {
    private String section;
    private String designation;

    private double p1;
    private double p2;
    private double p3;

    private double totalJour;
    private double totalMois;
    private double totalAnnee;

    // Getters & Setters
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public double getP1() { return p1; }
    public void setP1(double p1) { this.p1 = p1; }

    public double getP2() { return p2; }
    public void setP2(double p2) { this.p2 = p2; }

    public double getP3() { return p3; }
    public void setP3(double p3) { this.p3 = p3; }

    public double getTotalJour() { return totalJour; }
    public void setTotalJour(double totalJour) { this.totalJour = totalJour; }

    public double getTotalMois() { return totalMois; }
    public void setTotalMois(double totalMois) { this.totalMois = totalMois; }

    public double getTotalAnnee() { return totalAnnee; }
    public void setTotalAnnee(double totalAnnee) { this.totalAnnee = totalAnnee; }
}
