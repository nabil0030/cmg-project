package com.cmg.back.repository;

import com.cmg.back.model.DsNordRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface DsNordRecordRepository extends JpaRepository<DsNordRecord, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
        FROM DsNordRecord d
        WHERE d.date = :date
          AND d.hEntree = :hEntree
          AND d.hSortie = :hSortie
          AND d.transporteur = :transporteur
          AND d.numeroBL = :numeroBL
          AND d.immatricule = :immatricule
          AND d.tb = :tb
          AND d.tare = :tare
          AND d.poste = :poste
          AND d.lieuDeDecharge = :lieuDeDecharge
          AND ((:observation IS NULL AND d.observation IS NULL) OR d.observation = :observation)
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
            @Param("lieuDeDecharge") String lieuDeDecharge,
            @Param("observation") String observation
    );
    /*@Query("SELECT COALESCE(SUM(e.net), 0) FROM DsNordRecord e WHERE FUNCTION('to_char', e.date, 'YYYY-MM') = FUNCTION('to_char', :mois, 'YYYY-MM')")
    double sumForMonth(@Param("mois") LocalDate mois);*/
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM DsNordRecord e WHERE e.date = :jour")
    double sumJour(@Param("jour") LocalDate jour);
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM DsNordRecord e WHERE e.date >= :debut AND e.date <= :fin")
    double sumForMonth(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM DsNordRecord e WHERE e.date >= :debut AND e.date <= :fin")
    double sumAnnee(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.net), 0) FROM DsNordRecord e WHERE e.date = :date AND e.poste = :poste")
    double sumByDateAndPoste(@Param("date") LocalDate date, @Param("poste") int poste);

    @Query("SELECT COALESCE(SUM(e.net), 0) FROM DsNordRecord e WHERE e.date >= :start AND e.date <= :end AND e.poste = :poste")
    double sumByDateBetweenAndPoste(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("poste") int poste);





}
