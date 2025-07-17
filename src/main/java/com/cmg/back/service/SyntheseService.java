package com.cmg.back.service;

import com.cmg.back.dto.SyntheseDto;
import com.cmg.back.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class SyntheseService {

    private final DsClassiqueRepository dsRepo;
    private final DsNordRecordRepository nordRepo;
    private final ChauxViveLafargeRepository lafargeRepo;
    private final CBulkArgentifereRepository argentifereRepo;
    private final ChauxViveRepository chauxRepo;
    private final ControleBasculeHJDSRepository hjdsRepo;
    private final ControleBasculeKARepository kaRepo;
    private final ExpCuivreNordRepository cuivreNordRepo;
    private final ExpCuivreOncfRepository cuivreOncfRepo;
    private final ExpPbCmgOnfRepository pbCmgRepo;
    private final ExpZincOncfRepository zincOncfRepo;
    private final ExpZincSafiRepository zincSafiRepo;

    public SyntheseService(
            DsClassiqueRepository dsRepo,
            DsNordRecordRepository nordRepo,
            ChauxViveLafargeRepository lafargeRepo,
            CBulkArgentifereRepository argentifereRepo,
            ChauxViveRepository chauxRepo,
            ControleBasculeHJDSRepository hjdsRepo,
            ControleBasculeKARepository kaRepo,
            ExpCuivreNordRepository cuivreNordRepo,
            ExpCuivreOncfRepository cuivreOncfRepo,
            ExpPbCmgOnfRepository pbCmgRepo,
            ExpZincOncfRepository zincOncfRepo,
            ExpZincSafiRepository zincSafiRepo
    ) {
        this.dsRepo = dsRepo;
        this.nordRepo = nordRepo;
        this.lafargeRepo = lafargeRepo;
        this.argentifereRepo = argentifereRepo;
        this.chauxRepo = chauxRepo;
        this.hjdsRepo = hjdsRepo;
        this.kaRepo = kaRepo;
        this.cuivreNordRepo = cuivreNordRepo;
        this.cuivreOncfRepo = cuivreOncfRepo;
        this.pbCmgRepo = pbCmgRepo;
        this.zincOncfRepo = zincOncfRepo;
        this.zincSafiRepo = zincSafiRepo;
    }

    // === SYNTHÈSE JOUR ===
    public SyntheseDto getSyntheseJour(LocalDate date) {
        SyntheseDto dto = new SyntheseDto();
        dto.setDate(date);

        double ds = dsRepo.sumJour(date);
        double nord = nordRepo.sumJour(date);
        double lafarge = lafargeRepo.sumJour(date);
        double argentifere = argentifereRepo.sumJour(date);
        double chaux = chauxRepo.sumJour(date);
        double hjds = hjdsRepo.sumJour(date);
        double ka = kaRepo.sumJour(date);
        double cuivreNord = cuivreNordRepo.sumJour(date);
        double cuivreOncf = cuivreOncfRepo.sumJour(date);
        double pb = pbCmgRepo.sumJour(date);
        double zincOncf = zincOncfRepo.sumJour(date);
        double zincSafi = zincSafiRepo.sumJour(date);

        return remplirDto(dto, ds, nord, lafarge, argentifere, chaux, hjds, ka, cuivreNord, cuivreOncf, pb, zincOncf, zincSafi);
    }

    // === SYNTHÈSE MOIS ===
    public SyntheseDto getSyntheseMois(YearMonth mois) {
        SyntheseDto dto = new SyntheseDto();
        dto.setMois(mois);

        LocalDate debut = mois.atDay(1);
        LocalDate fin = mois.atEndOfMonth();

        double ds = dsRepo.sumForMonth(debut, fin);
        double nord = nordRepo.sumForMonth(debut, fin);
        double lafarge = lafargeRepo.sumForMonth(debut, fin);
        double argentifere = argentifereRepo.sumForMonth(debut, fin);
        double chaux = chauxRepo.sumForMonth(debut, fin);
        double hjds = hjdsRepo.sumForMonth(debut, fin);
        double ka = kaRepo.sumForMonth(debut, fin);
        double cuivreNord = cuivreNordRepo.sumForMonth(debut, fin);
        double cuivreOncf = cuivreOncfRepo.sumForMonth(debut, fin);
        double pb = pbCmgRepo.sumForMonth(debut, fin);
        double zincOncf = zincOncfRepo.sumForMonth(debut, fin);
        double zincSafi = zincSafiRepo.sumForMonth(debut, fin);

        return remplirDto(dto, ds, nord, lafarge, argentifere, chaux, hjds, ka, cuivreNord, cuivreOncf, pb, zincOncf, zincSafi);
    }

    // === SYNTHÈSE ANNUELLE ===
    public SyntheseDto getSyntheseAnnee(int annee) {
        SyntheseDto dto = new SyntheseDto();
        dto.setAnnee(annee);

        LocalDate debut = LocalDate.of(annee, 1, 1);
        LocalDate fin = LocalDate.of(annee, 12, 31);

        double ds = dsRepo.sumAnnee(debut, fin);
        double nord = nordRepo.sumAnnee(debut, fin);
        double lafarge = lafargeRepo.sumAnnee(debut, fin);
        double argentifere = argentifereRepo.sumAnnee(debut, fin);
        double chaux = chauxRepo.sumAnnee(debut, fin);
        double hjds = hjdsRepo.sumAnnee(debut, fin);
        double ka = kaRepo.sumAnnee(debut, fin);
        double cuivreNord = cuivreNordRepo.sumAnnee(debut, fin);
        double cuivreOncf = cuivreOncfRepo.sumAnnee(debut, fin);
        double pb = pbCmgRepo.sumAnnee(debut, fin);
        double zincOncf = zincOncfRepo.sumAnnee(debut, fin);
        double zincSafi = zincSafiRepo.sumAnnee(debut, fin);

        return remplirDto(dto, ds, nord, lafarge, argentifere, chaux, hjds, ka, cuivreNord, cuivreOncf, pb, zincOncf, zincSafi);
    }

    private SyntheseDto remplirDto(SyntheseDto dto, double... values) {
        dto.setDsClassique(values[0]);
        dto.setDsNord(values[1]);
        dto.setChauxViveLafarge(values[2]);
        dto.setCbulkArgentifere(values[3]);
        dto.setChauxVive(values[4]);
        dto.setControleBasculeHJDS(values[5]);
        dto.setControleBasculeKA(values[6]);
        dto.setExpCuivreNord(values[7]);
        dto.setExpCuivreOncf(values[8]);
        dto.setExpPbCmgOnf(values[9]);
        dto.setExpZincOncf(values[10]);
        dto.setExpZincSafi(values[11]);

        double total = 0.0;
        for (double v : values) total += v;
        dto.setGlobal(BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP));

        return dto;
    }
}
