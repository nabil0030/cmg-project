package com.cmg.back.repository;

import com.cmg.back.model.ChauxViveLafarge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChauxViveLafargeRepository extends JpaRepository<ChauxViveLafarge, Long> {

    @Query("SELECT COUNT(c) > 0 FROM ChauxViveLafarge c WHERE " +
            "c.date = ?1 AND c.hEntree = ?2 AND c.hSortie = ?3 AND " +
            "c.transporteur = ?4 AND c.numeroBL = ?5 AND c.immatricule = ?6 AND " +
            "c.tb = ?7 AND c.tare = ?8 AND c.poste = ?9 AND " +
            "c.lieuChargement = ?10 AND c.lieuDechargement = ?11")
    boolean existsDuplicate(String date, String hEntree, String hSortie,
                            String transporteur, String numeroBL, String immatricule,
                            double tb, double tare, int poste,
                            String lieuChargement, String lieuDechargement);

    java.util.List<ChauxViveLafarge> findAllByOrderByDateAsc();
}
