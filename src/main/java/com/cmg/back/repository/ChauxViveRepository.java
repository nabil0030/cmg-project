package com.cmg.back.repository;

import com.cmg.back.model.ChauxVive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChauxViveRepository extends JpaRepository<ChauxVive, Long> {

    @Query("SELECT COUNT(c) > 0 FROM ChauxVive c WHERE " +
            "c.date = :date AND c.hEntree = :hEntree AND c.hSortie = :hSortie AND " +
            "c.transporteur = :transporteur AND c.numeroBL = :numeroBL AND " +
            "c.immatricule = :immatricule AND c.tb = :tb AND c.tare = :tare AND " +
            "c.poste = :poste AND c.lieuChargement = :lieuChargement AND c.lieuDechargement = :lieuDechargement")
    boolean isDuplicate(
            @Param("date") String date,
            @Param("hEntree") java.time.LocalTime hEntree,
            @Param("hSortie") java.time.LocalTime hSortie,
            @Param("transporteur") String transporteur,
            @Param("numeroBL") String numeroBL,
            @Param("immatricule") String immatricule,
            @Param("tb") Double tb,
            @Param("tare") Double tare,
            @Param("poste") Integer poste,
            @Param("lieuChargement") String lieuChargement,
            @Param("lieuDechargement") String lieuDechargement
    );
}
