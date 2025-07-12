package com.cmg.back.repository;

import com.cmg.back.model.ControleBasculeHJDS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate; // ✅ ajoute cette importation

public interface ControleBasculeHJDSRepository extends JpaRepository<ControleBasculeHJDS, Long> {

    boolean existsByDateAndTransporteurAndNumeroBLAndImmatriculeAndNetDSAndNetCMG(
            LocalDate date,                       // ⬅️ ici : LocalDate, pas String
            String transporteur,
            String numeroBL,
            String immatricule,
            Double netDS,
            Double netCMG
    );

}
