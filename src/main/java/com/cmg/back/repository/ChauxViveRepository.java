package com.cmg.back.repository;

import com.cmg.back.model.ChauxVive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ChauxViveRepository extends JpaRepository<ChauxVive, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
        FROM ChauxVive e
        WHERE e.date = :date
          AND e.hEntree = :hEntree
          AND e.hSortie = :hSortie
          AND e.transporteur = :transporteur
          AND e.numeroBL = :numeroBL
          AND e.immat = :immat
          AND e.tb = :tb
          AND e.tare = :tare
          AND e.poste = :poste
          AND e.netCosumar = :netCosumar
          AND e.lieuChargement = :lieuChargement
          AND e.lieuDechargement = :lieuDechargement
          AND ((:observation IS NULL AND e.observation IS NULL) OR e.observation = :observation)
    """)
    boolean existsDuplicate(
            @Param("date") LocalDate date,
            @Param("hEntree") LocalTime hEntree,
            @Param("hSortie") LocalTime hSortie,
            @Param("transporteur") String transporteur,
            @Param("numeroBL") String numeroBL,
            @Param("immat") String immat,
            @Param("tb") Double tb,
            @Param("tare") Double tare,
            @Param("poste") Integer poste,
            @Param("netCosumar") Double netCosumar,
            @Param("lieuChargement") String lieuChargement,
            @Param("lieuDechargement") String lieuDechargement,
            @Param("observation") String observation
    );
}
