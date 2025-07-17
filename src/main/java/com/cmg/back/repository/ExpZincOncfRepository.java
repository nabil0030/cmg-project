package com.cmg.back.repository;

import com.cmg.back.model.ExpZincOncf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ExpZincOncfRepository extends JpaRepository<ExpZincOncf, Long> {
    @Query("SELECT COALESCE(SUM(e.tnH), 0) FROM ExpZincOncf e WHERE e.date = :jour")
    double sumJour(@Param("jour") LocalDate jour);
    @Query("SELECT COALESCE(SUM(e.tnH), 0) FROM ExpZincOncf e WHERE e.date >= :debut AND e.date <= :fin")
    double sumForMonth(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
    @Query("SELECT COALESCE(SUM(e.tnH), 0) FROM ExpZincOncf e WHERE e.date >= :debut AND e.date <= :fin")
    double sumAnnee(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);




}
