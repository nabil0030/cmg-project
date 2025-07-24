package com.cmg.back.repository;

import com.cmg.back.model.CBulkArgentifere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CBulkArgentifereRepository extends JpaRepository<CBulkArgentifere, Long> {

    Optional<CBulkArgentifere> findByDateAndHeureEntreeAndHeureSortieAndTransporteurAndNumeroBLAndMatriculeAndTbAndTareAndNumeroContenaireAndPosteAndLieuDechargementAndObservation(
            LocalDate date, // ✅ Corrigé ici
            String heureEntree,
            String heureSortie,
            String transporteur,
            String numeroBL,
            String matricule,
            Double tb,
            Double tare,
            String numeroContenaire,
            Integer poste,
            String lieuDechargement,
            String observation
    );

    @Query("SELECT COALESCE(SUM(e.tnh), 0) FROM CBulkArgentifere e WHERE e.date = :jour")
    double sumJour(@Param("jour") LocalDate jour);
    @Query("SELECT COALESCE(SUM(e.tnh), 0) FROM CBulkArgentifere e WHERE e.date >= :debut AND e.date <= :fin")
    double sumForMonth(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.tnh), 0) FROM CBulkArgentifere e WHERE e.date >= :debut AND e.date <= :fin")
    double sumAnnee(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);

    @Query("SELECT COALESCE(SUM(e.tnh), 0) FROM CBulkArgentifere e WHERE e.date = :date AND e.poste = :poste")
    double sumByDateAndPoste(@Param("date") LocalDate date, @Param("poste") int poste);

    @Query("SELECT COALESCE(SUM(e.tnh), 0) FROM CBulkArgentifere e WHERE e.date >= :start AND e.date <= :end AND e.poste = :poste")
    double sumByDateBetweenAndPoste(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("poste") int poste);

}
