package com.cmg.back.repository;

import com.cmg.back.model.LieuChargement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LieuChargementRepository extends JpaRepository<LieuChargement, Long> {
}
