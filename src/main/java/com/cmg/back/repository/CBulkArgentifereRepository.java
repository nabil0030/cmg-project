package com.cmg.back.repository;

import com.cmg.back.model.CBulkArgentifere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CBulkArgentifereRepository extends JpaRepository<CBulkArgentifere, Long> {

    Optional<CBulkArgentifere> findByDateAndHeureEntreeAndHeureSortieAndTransporteurAndNumeroBLAndMatriculeAndTbAndTareAndNumeroContenaireAndPosteAndLieuDechargementAndObservation(
            String date, String heureEntree, String heureSortie, String transporteur,
            String numeroBL, String matricule, Double tb, Double tare,
            String numeroContenaire, Integer poste, String lieuDechargement, String observation
    );
}
