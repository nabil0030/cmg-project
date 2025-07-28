package com.cmg.back.repository;

import com.cmg.back.model.LieuDechargement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LieuDechargementRepository extends JpaRepository<LieuDechargement, Long> {
}
