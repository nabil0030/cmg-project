package com.cmg.back.repository;

import com.cmg.back.model.ChauxViveLafarge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChauxViveLafargeRepository extends JpaRepository<ChauxViveLafarge, Long> {

    Optional<ChauxViveLafarge> findByDateAndHeureEntreeAndHeureSortieAndTransporteurAndNumeroBLAndImmatriculeAndTbAndTareAndNetLafargeAndPosteAndLieuChargementAndLieuDechargementAndObservation(
            String date, String heureEntree, String heureSortie, String transporteur,
            String numeroBL, String immatricule, Double tb, Double tare, Double netLafarge,
            Integer poste, String lieuChargement, String lieuDechargement, String observation
    );
}
