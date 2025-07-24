package com.cmg.back.repository;

import com.cmg.back.model.ControleBasculeKA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ControleBasculeKARepository extends JpaRepository<ControleBasculeKA, Long> {

    @Query("""
        SELECT COUNT(c) > 0 FROM ControleBasculeKA c
        WHERE c.date = :date
          AND c.hEntree = :hEntree
          AND c.hSortie = :hSortie
          AND c.transporteur = :transporteur
          AND c.numeroBL = :numeroBL
          AND c.immatricule = :immatricule
          AND c.tb = :tb
          AND c.tare = :tare
          AND c.poste = :poste
          AND c.lieuDeDecharge = :lieuDeDecharge
    """)
    boolean isDuplicate(
            @Param("date") LocalDate date,
            @Param("hEntree") LocalTime hEntree,
            @Param("hSortie") LocalTime hSortie,
            @Param("transporteur") String transporteur,
            @Param("numeroBL") String numeroBL,
            @Param("immatricule") String immatricule,
            @Param("tb") Double tb,
            @Param("tare") Double tare,
            @Param("poste") Integer poste,
            @Param("lieuDeDecharge") String lieuDeDecharge
    );
   /* @Query("SELECT COALESCE(SUM(e.net), 0) FROM ControleBasculeKA e WHERE FUNCTION('to_char', e.date, 'YYYY-MM') = FUNCTION('to_char', :mois, 'YYYY-MM')")
    double sumForMonth(@Param("mois") LocalDate mois);*/
   @Query("SELECT COALESCE(SUM(e.net), 0) FROM ControleBasculeKA e WHERE e.date = :jour")
   double sumJour(@Param("jour") LocalDate jour);
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM ControleBasculeKA e WHERE e.date >= :debut AND e.date <= :fin")
    double sumForMonth(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM ControleBasculeKA e WHERE e.date >= :debut AND e.date <= :fin")
    double sumAnnee(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM ControleBasculeKA e WHERE e.date = :date AND e.poste = :poste")
    double sumByDateAndPoste(@Param("date") LocalDate date, @Param("poste") int poste);

    @Query("SELECT COALESCE(SUM(e.net), 0) FROM ControleBasculeKA e WHERE e.date >= :start AND e.date <= :end AND e.poste = :poste")
    double sumByDateBetweenAndPoste(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("poste") int poste);





}
