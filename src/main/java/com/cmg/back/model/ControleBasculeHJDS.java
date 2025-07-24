package com.cmg.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class ControleBasculeHJDS {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String transporteur;
    @Column(unique = true)
    private String numeroBL;
    private String immatricule;

    /** ───── camelCase homogène ───── */
    private Double netDs;
    private Double netCmg;

    private Double ecart;
    private String observation;

    /* ---------- Getters / Setters ---------- */
    public Long getId()                    { return id; }
    public void setId(Long id)             { this.id = id; }

    public LocalDate getDate()             { return date; }
    public void setDate(LocalDate date)    { this.date = date; }

    public String getTransporteur()        { return transporteur; }
    public void setTransporteur(String t)  { this.transporteur = t; }

    public String getNumeroBL()            { return numeroBL; }
    public void setNumeroBL(String n)      { this.numeroBL = n; }

    public String getImmatricule()         { return immatricule; }
    public void setImmatricule(String i)   { this.immatricule = i; }

    public Double getNetDs()               { return netDs; }
    public void setNetDs(Double v)         { this.netDs = v; }

    public Double getNetCmg()              { return netCmg; }
    public void setNetCmg(Double v)        { this.netCmg = v; }

    public Double getEcart()               { return ecart; }
    public void setEcart(Double e)         { this.ecart = e; }

    public String getObservation()         { return observation; }
    public void setObservation(String o)   { this.observation = o; }
}
