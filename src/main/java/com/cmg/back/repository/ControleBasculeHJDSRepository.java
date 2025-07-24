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
    @Query("SELECT COALESCE(SUM(e.netCmg), 0) FROM ControleBasculeHJDS e WHERE e.date = :date")
    double sumByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(e.netCmg), 0) FROM ControleBasculeHJDS e WHERE e.date >= :start AND e.date <= :end")
    double sumByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);
    @Query("""
    SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
    FROM ControleBasculeHJDS c
    WHERE c.date = :date
      AND c.transporteur = :transporteur
      AND c.numeroBL = :numeroBL
      AND c.immatricule = :immatricule
      AND c.netCmg = :netCmg
      AND ((:observation IS NULL AND c.observation IS NULL) OR c.observation = :observation)
""")
    boolean isDuplicate(
            @Param("date") LocalDate date,
            @Param("transporteur") String transporteur,
            @Param("numeroBL") String numeroBL,
            @Param("immatricule") String immatricule,
            @Param("netCmg") Double netCmg,
            @Param("observation") String observation
    );

    boolean existsByNumeroBL(String numeroBL);

}
