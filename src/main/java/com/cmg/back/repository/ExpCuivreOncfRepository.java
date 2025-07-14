package com.cmg.back.repository;

import com.cmg.back.model.ExpCuivreOncf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ExpCuivreOncfRepository extends JpaRepository<ExpCuivreOncf, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
        FROM ExpCuivreOncf e
        WHERE e.date = :date
          AND e.hEntree = :hEntree
          AND e.hSortie = :hSortie
          AND e.transporteur = :transporteur
          AND e.numeroBL = :numeroBL
          AND e.matricule = :matricule
          AND e.tb = :tb
          AND e.tare = :tare
          AND e.numeroContenaire = :numeroContenaire
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
            @Param("numeroContenaire") String numeroContenaire,
            @Param("poste") Integer poste,
            @Param("lieuDeDechargement") String lieuDeDechargement,
            @Param("observation") String observation
    );
}
