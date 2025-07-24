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
    @Autowired ControleBasculeHJDSRepository controleBasculeHJDSRepository;
    @Autowired ChauxViveLafargeRepository chauxViveLafargeRepository;
    @Autowired ChauxViveRepository chauxViveRepository;
    @Autowired CBulkArgentifereRepository cBulkArgentifereRepository;
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

        for (int poste = 1; poste <= 3; poste++) {
            rapport.add(line("Arrivages TV Chantier", "DS", poste,
                    dsClassiqueRepository.sumByDateAndPoste(date, poste),
                    dsClassiqueRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    dsClassiqueRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Arrivages TV Chantier", "DS Nord", poste,
                    dsNordRepository.sumByDateAndPoste(date, poste),
                    dsNordRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    dsNordRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Arrivages TV Chantier", "KA", poste,
                    controleBasculeKARepository.sumByDateAndPoste(date, poste),
                    controleBasculeKARepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    controleBasculeKARepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Arrivages Chaux", "Chaux Lafarge", poste,
                    chauxViveLafargeRepository.sumByDateAndPoste(date, poste),
                    chauxViveLafargeRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    chauxViveLafargeRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Arrivages Chaux", "Chaux Cosumar", poste,
                    chauxViveRepository.sumByDateAndPoste(date, poste),
                    chauxViveRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    chauxViveRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Expéditions CC", "Zn TC ONCF", poste,
                    expZincOncfRepository.sumByDateAndPoste(date, poste),
                    expZincOncfRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    expZincOncfRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Expéditions CC", "Zn Vrac p Safi", poste,
                    expZincSafiRepository.sumByDateAndPoste(date, poste),
                    expZincSafiRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    expZincSafiRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Expéditions CC", "Pb ONCF", poste,
                    expPbCmgOnfRepository.sumByDateAndPoste(date, poste),
                    expPbCmgOnfRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    expPbCmgOnfRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Expéditions CC", "Cu Nord ONCF", poste,
                    expCuivreNordRepository.sumByDateAndPoste(date, poste),
                    expCuivreNordRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    expCuivreNordRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));

            rapport.add(line("Expéditions CC", "Cu ONCF", poste,
                    expCuivreOncfRepository.sumByDateAndPoste(date, poste),
                    expCuivreOncfRepository.sumByDateBetweenAndPoste(debutMois, finMois, poste),
                    expCuivreOncfRepository.sumByDateBetweenAndPoste(debutAnnee, finAnnee, poste)));
        }

        return rapport;
    }

    private RapportHajjarDto line(String section, String designation, int poste, double jour, double mois, double annee) {
        return new RapportHajjarDto(
                section,
                designation,
                "P" + poste,
                jour,
                jour,
                mois,
                annee
        );
    }
}
