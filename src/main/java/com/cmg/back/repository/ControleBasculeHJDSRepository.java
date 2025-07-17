package com.cmg.back.repository;

import com.cmg.back.model.ControleBasculeHJDS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ControleBasculeHJDSRepository
        extends JpaRepository<ControleBasculeHJDS, Long> {

    /* ----- détection de duplicata ----------------------------------- */
    boolean existsByDateAndTransporteurAndNumeroBLAndImmatriculeAndNetDsAndNetCmg(
            LocalDate date, String transporteur, String numeroBL,
            String immatricule, Double netDs, Double netCmg);

    @Query("SELECT COALESCE(SUM(e.netCmg), 0) FROM ControleBasculeHJDS e WHERE e.date = :jour")
    double sumJour(@Param("jour") LocalDate jour);
    @Query("SELECT COALESCE(SUM(e.netCmg), 0) FROM ControleBasculeHJDS e WHERE e.date >= :debut AND e.date <= :fin")
    double sumForMonth(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.netCmg), 0) FROM ControleBasculeHJDS e WHERE e.date >= :debut AND e.date <= :fin")
    double sumAnnee(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);



}
