package com.cmg.back.service;

import com.cmg.back.dto.RapportHajjarDto;
import com.cmg.back.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class RapportHajjarService {

    @Autowired DsClassiqueRepository dsClassiqueRepository;
    @Autowired DsNordRecordRepository dsNordRepository;
    @Autowired ControleBasculeKARepository controleBasculeKARepository;
    @Autowired ChauxViveLafargeRepository chauxViveLafargeRepository;
    @Autowired ChauxViveRepository chauxViveRepository;
    @Autowired ExpZincOncfRepository expZincOncfRepository;
    @Autowired ExpZincSafiRepository expZincSafiRepository;
    @Autowired ExpPbCmgOnfRepository expPbCmgOnfRepository;
    @Autowired ExpCuivreNordRepository expCuivreNordRepository;
    @Autowired ExpCuivreOncfRepository expCuivreOncfRepository;

    public List<RapportHajjarDto> genererRapport(LocalDate date) {
        List<RapportHajjarDto> rapport = new ArrayList<>();

        LocalDate debutMois = date.withDayOfMonth(1);
        LocalDate finMois = YearMonth.from(date).atEndOfMonth();
        LocalDate debutAnnee = date.withDayOfYear(1);
        LocalDate finAnnee = date.withMonth(12).withDayOfMonth(31);

        // Section Arrivages TV
        rapport.add(creerLigne("Arrivages TV Chantier", "DS", dsClassiqueRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Arrivages TV Chantier", "DS Nord", dsNordRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Arrivages TV Chantier", "KA", controleBasculeKARepository, date, debutMois, finMois, debutAnnee, finAnnee));

        // Section Arrivages Chaux
        rapport.add(creerLigne("Arrivages Chaux", "Chaux Lafarge", chauxViveLafargeRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Arrivages Chaux", "Chaux Cosumar", chauxViveRepository, date, debutMois, finMois, debutAnnee, finAnnee));

        // Section Expéditions CC
        rapport.add(creerLigne("Expéditions CC", "Zn TC ONCF", expZincOncfRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Expéditions CC", "Zn Vrac p Safi", expZincSafiRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Expéditions CC", "Pb ONCF", expPbCmgOnfRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Expéditions CC", "Cu Nord ONCF", expCuivreNordRepository, date, debutMois, finMois, debutAnnee, finAnnee));
        rapport.add(creerLigne("Expéditions CC", "Cu ONCF", expCuivreOncfRepository, date, debutMois, finMois, debutAnnee, finAnnee));

        return rapport;
    }

    private RapportHajjarDto creerLigne(
            String section,
            String designation,
            Object repo,
            LocalDate date,
            LocalDate debutMois,
            LocalDate finMois,
            LocalDate debutAnnee,
            LocalDate finAnnee
    ) {
        double p1 = getSumByPoste(repo, date, 1);
        double p2 = getSumByPoste(repo, date, 2);
        double p3 = getSumByPoste(repo, date, 3);

        double totalJour = p1 + p2 + p3;
        double totalMois = getSumByDateRange(repo, debutMois, finMois);
        double totalAnnee = getSumByDateRange(repo, debutAnnee, finAnnee);

        RapportHajjarDto dto = new RapportHajjarDto();
        dto.setSection(section);
        dto.setDesignation(designation);
        dto.setP1(p1);
        dto.setP2(p2);
        dto.setP3(p3);
        dto.setTotalJour(totalJour);
        dto.setTotalMois(totalMois);
        dto.setTotalAnnee(totalAnnee);
        return dto;
    }

    private double getSumByPoste(Object repo, LocalDate date, int poste) {
        try {
            return (double) repo.getClass()
                    .getMethod("sumByDateAndPoste", LocalDate.class, int.class)
                    .invoke(repo, date, poste);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double getSumByDateRange(Object repo, LocalDate debut, LocalDate fin) {
        try {
            return (double) repo.getClass()
                    .getMethod("sumByDateBetweenAndPoste", LocalDate.class, LocalDate.class, int.class)
                    .invoke(repo, debut, fin, 1)
                    + (double) repo.getClass()
                    .getMethod("sumByDateBetweenAndPoste", LocalDate.class, LocalDate.class, int.class)
                    .invoke(repo, debut, fin, 2)
                    + (double) repo.getClass()
                    .getMethod("sumByDateBetweenAndPoste", LocalDate.class, LocalDate.class, int.class)
                    .invoke(repo, debut, fin, 3);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
