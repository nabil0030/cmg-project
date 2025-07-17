package com.cmg.back.repository;

import com.cmg.back.model.ExpZincSafi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ExpZincSafiRepository extends JpaRepository<ExpZincSafi, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
        FROM ExpZincSafi e
        WHERE e.date = :date
          AND e.hEntree = :hEntree
          AND e.hSortie = :hSortie
          AND e.transporteur = :transporteur
          AND e.numeroBL = :numeroBL
          AND e.matricule = :matricule
          AND e.tb = :tb
          AND e.tare = :tare
          AND e.poste = :poste
          AND e.lieuDeDechargement = :lieuDeDechargement
          AND ((:observation IS NULL AND e.observation IS NULL) OR e.observation = :observation)
    """)
    boolean existsDuplicate(
            @Param("date") LocalDate date,
            @Param("hEntree") LocalTime hEntree,
            @Param("hSortie") LocalTime hSortie,
            @Param("transporteur") String transporteur,
            @Param("numeroBL") String numeroBL,
            @Param("matricule") String matricule,
            @Param("tb") Double tb,
            @Param("tare") Double tare,
            @Param("poste") Integer poste,
            @Param("lieuDeDechargement") String lieuDeDechargement,
            @Param("observation") String observation
    );
    @Query("SELECT COALESCE(SUM(e.tnH), 0) FROM ExpZincSafi e WHERE e.date = :jour")
    double sumJour(@Param("jour") LocalDate jour);
    @Query("SELECT COALESCE(SUM(e.tnH), 0) FROM ExpZincSafi e WHERE e.date >= :debut AND e.date <= :fin")
    double sumForMonth(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.tnH), 0) FROM ExpZincSafi e WHERE e.date >= :debut AND e.date <= :fin")
    double sumAnnee(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);





}
