package com.cmg.back.service; // Assurez-vous que le package est correct

import com.cmg.back.dto.SyntheseCumulAnnuelGroupesDTO;
import com.cmg.back.dto.SyntheseDailyDTO;
import com.cmg.back.dto.SyntheseDto;
import com.cmg.back.repository.*; // Importez tous vos repositories
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal; // Import pour BigDecimal
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SyntheseService {

    // Injection de tous vos repositories
    private final DsClassiqueRepository dsClassiqueRepository;
    private final DsNordRecordRepository dsNordRepository; // Corrigé ici
    private final ControleBasculeKARepository controleBasculeKARepository;
    private final ChauxViveRepository chauxViveRepository;
    private final ChauxViveLafargeRepository chauxViveLafargeRepository;
    private final ExpCuivreOncfRepository expCuivreOncfRepository;
    private final ExpCuivreNordRepository expCuivreNordRepository;
    private final ExpPbCmgOnfRepository expPbCmgOnfRepository;
    private final CBulkArgentifereRepository cBulkArgentifereRepository;
    private final ExpZincOncfRepository expZincOncfRepository;
    private final ExpZincSafiRepository expZincSafiRepository;
    private final ControleBasculeHJDSRepository controleBasculeHJDSRepository;

    @Autowired
    public SyntheseService(
            DsClassiqueRepository dsClassiqueRepository,
            DsNordRecordRepository dsNordRepository, // Corrigé ici
            ControleBasculeKARepository controleBasculeKARepository,
            ChauxViveRepository chauxViveRepository,
            ChauxViveLafargeRepository chauxViveLafargeRepository,
            ExpCuivreOncfRepository expCuivreOncfRepository,
            ExpCuivreNordRepository expCuivreNordRepository,
            ExpPbCmgOnfRepository expPbCmgOnfRepository,
            CBulkArgentifereRepository cBulkArgentifereRepository,
            ExpZincOncfRepository expZincOncfRepository,
            ExpZincSafiRepository expZincSafiRepository,
            ControleBasculeHJDSRepository controleBasculeHJDSRepository) {
        this.dsClassiqueRepository = dsClassiqueRepository;
        this.dsNordRepository = dsNordRepository;
        this.controleBasculeKARepository = controleBasculeKARepository;
        this.chauxViveRepository = chauxViveRepository;
        this.chauxViveLafargeRepository = chauxViveLafargeRepository;
        this.expCuivreOncfRepository = expCuivreOncfRepository;
        this.expCuivreNordRepository = expCuivreNordRepository;
        this.expPbCmgOnfRepository = expPbCmgOnfRepository;
        this.cBulkArgentifereRepository = cBulkArgentifereRepository;
        this.expZincOncfRepository = expZincOncfRepository;
        this.expZincSafiRepository = expZincSafiRepository;
        this.controleBasculeHJDSRepository = controleBasculeHJDSRepository;
    }

    /**
     * Récupère les données de synthèse pour une date journalière.
     * Utilise la méthode 'sumJour' des repositories.
     *
     * @param date La date pour laquelle récupérer les données.
     * @return Un objet SyntheseDailyDTO.
     */
    public SyntheseDailyDTO getDailySynthese(LocalDate date) {
        SyntheseDailyDTO dto = new SyntheseDailyDTO();

        dto.setDsClassique(Objects.requireNonNullElse(dsClassiqueRepository.sumJour(date), 0.0));
        dto.setDsNord(Objects.requireNonNullElse(dsNordRepository.sumJour(date), 0.0));
        dto.setControleBasculeKA(Objects.requireNonNullElse(controleBasculeKARepository.sumJour(date), 0.0));
        dto.setChauxVive(Objects.requireNonNullElse(chauxViveRepository.sumJour(date), 0.0));
        dto.setChauxViveLafarge(Objects.requireNonNullElse(chauxViveLafargeRepository.sumJour(date), 0.0));
        dto.setControleBasculeHJDS(Objects.requireNonNullElse(controleBasculeHJDSRepository.sumJour(date), 0.0));

        dto.setExpCuivreOncf(Objects.requireNonNullElse(expCuivreOncfRepository.sumJour(date), 0.0));
        dto.setExpCuivreNord(Objects.requireNonNullElse(expCuivreNordRepository.sumJour(date), 0.0));
        dto.setExpPbCmgOnf(Objects.requireNonNullElse(expPbCmgOnfRepository.sumJour(date), 0.0));
        dto.setCbulkArgentifere(Objects.requireNonNullElse(cBulkArgentifereRepository.sumJour(date), 0.0));
        dto.setExpZincOncf(Objects.requireNonNullElse(expZincOncfRepository.sumJour(date), 0.0));
        dto.setExpZincSafi(Objects.requireNonNullElse(expZincSafiRepository.sumJour(date), 0.0));

        return dto;
    }


    /**
     * Récupère les données de synthèse mensuelle pour une année donnée.
     * Utilisé pour le tableau principal et l'export Excel.
     * Utilise la méthode 'sumForMonth' des repositories.
     *
     * @param annee L'année pour laquelle générer le résumé.
     * @return Une Map où la clé est le nom du mois (ex: "JANVIER") et la valeur est une liste de doubles.
     * La liste contient les valeurs suivantes dans l'ordre:
     * [0:DRAA SFAR, 1:DRAA NORD, 2:KOUDIAT AICHA, 3:COSUMAR, 4:LAFARGE, 5:Total Entrée,
     * 6:CU MRK, 7:CU NORD, 8:PB MRK, 9:PB CASA, 10:ZN ONCF, 11:ZN PORT (0.0), 12:ZN VRAC (0.0), 13:ZN CBULK, 14:Total Expédition]
     */
    public Map<String, List<Double>> getMonthlySummaryForExcelExport(int annee) {
        Map<String, List<Double>> result = new LinkedHashMap<>();

        String[] frenchMonthNames = {
                "JANVIER", "FÉVRIER", "MARS", "AVRIL", "MAI", "JUIN",
                "JUILLET", "AOÛT", "SEPTEMBRE", "OCTOBRE", "NOVEMBRE", "DÉCEMBRE"
        };

        for (int mois = 1; mois <= 12; mois++) {
            YearMonth ym = YearMonth.of(annee, mois);
            LocalDate debut = ym.atDay(1);
            LocalDate fin = ym.atEndOfMonth();

            List<Double> ligne = new ArrayList<>();

            // LES ENTRÉES (5 valeurs individuelles + 1 total)
            double dsClassique = Objects.requireNonNullElse(dsClassiqueRepository.sumForMonth(debut, fin), 0.0);
            double dsNord = Objects.requireNonNullElse(dsNordRepository.sumForMonth(debut, fin), 0.0);
            double ka = Objects.requireNonNullElse(controleBasculeKARepository.sumForMonth(debut, fin), 0.0);
            double chaux = Objects.requireNonNullElse(chauxViveRepository.sumForMonth(debut, fin), 0.0);
            double lafarge = Objects.requireNonNullElse(chauxViveLafargeRepository.sumForMonth(debut, fin), 0.0);
            double totalEntree = dsClassique + dsNord + ka + chaux + lafarge;

            ligne.add(dsClassique);
            ligne.add(dsNord);
            ligne.add(ka);
            ligne.add(chaux);
            ligne.add(lafarge);
            ligne.add(totalEntree);

            // LES EXPÉDITIONS (8 valeurs individuelles + 1 total)
            double expCuivreOncf = Objects.requireNonNullElse(expCuivreOncfRepository.sumForMonth(debut, fin), 0.0);
            double expCuivreNord = Objects.requireNonNullElse(expCuivreNordRepository.sumForMonth(debut, fin), 0.0);
            double expPbCmgOnf = Objects.requireNonNullElse(expPbCmgOnfRepository.sumForMonth(debut, fin), 0.0);
            double cbulkArgentifere = Objects.requireNonNullElse(cBulkArgentifereRepository.sumForMonth(debut, fin), 0.0);
            double expZincOncf = Objects.requireNonNullElse(expZincOncfRepository.sumForMonth(debut, fin), 0.0);
            double expZincSafi = Objects.requireNonNullElse(expZincSafiRepository.sumForMonth(debut, fin), 0.0);

            // Ces valeurs sont mises à 0.0 car elles ne sont pas gérées par un repository spécifique dans votre modèle.
            double znPort = 0.0;
            double znVrac = 0.0;

            double totalExpedition = expCuivreOncf + expCuivreNord + expPbCmgOnf + cbulkArgentifere +
                    expZincOncf + znPort + znVrac + expZincSafi;

            ligne.add(expCuivreOncf);
            ligne.add(expCuivreNord);
            ligne.add(expPbCmgOnf);
            ligne.add(cbulkArgentifere);
            ligne.add(expZincOncf);
            ligne.add(znPort); // ZINC PORT (Hardcoded to 0.0)
            ligne.add(znVrac); // ZINC EN VRAC (Hardcoded to 0.0)
            ligne.add(expZincSafi);
            ligne.add(totalExpedition);

            result.put(frenchMonthNames[mois - 1], ligne);
        }
        return result;
    }


    /**
     * Récupère la synthèse annuelle pour une année donnée.
     * Utilise la méthode 'sumAnnee' des repositories.
     *
     * @param annee L'année pour laquelle récupérer la synthèse.
     * @return Un objet SyntheseDto.
     */
    public SyntheseDto getAnnualSynthese(int annee) {
        LocalDate debutAnnee = LocalDate.of(annee, 1, 1);
        LocalDate finAnnee = LocalDate.of(annee, 12, 31);

        SyntheseDto dto = new SyntheseDto();

        dto.setAnnee(annee); // Définir l'année dans le DTO

        // Entrées
        dto.setDsClassique(Objects.requireNonNullElse(dsClassiqueRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setDsNord(Objects.requireNonNullElse(dsNordRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setChauxViveLafarge(Objects.requireNonNullElse(chauxViveLafargeRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setChauxVive(Objects.requireNonNullElse(chauxViveRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setControleBasculeKA(Objects.requireNonNullElse(controleBasculeKARepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setControleBasculeHJDS(Objects.requireNonNullElse(controleBasculeHJDSRepository.sumAnnee(debutAnnee, finAnnee), 0.0));

        // Expéditions
        dto.setCbulkArgentifere(Objects.requireNonNullElse(cBulkArgentifereRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setExpCuivreNord(Objects.requireNonNullElse(expCuivreNordRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setExpCuivreOncf(Objects.requireNonNullElse(expCuivreOncfRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setExpPbCmgOnf(Objects.requireNonNullElse(expPbCmgOnfRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setExpZincOncf(Objects.requireNonNullElse(expZincOncfRepository.sumAnnee(debutAnnee, finAnnee), 0.0));
        dto.setExpZincSafi(Objects.requireNonNullElse(expZincSafiRepository.sumAnnee(debutAnnee, finAnnee), 0.0));

        // Calcul du global avec BigDecimal
        BigDecimal totalGlobal = BigDecimal.ZERO;
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getDsClassique()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getDsNord()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getChauxViveLafarge()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getCbulkArgentifere()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getChauxVive()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getControleBasculeKA()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getControleBasculeHJDS()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getExpCuivreNord()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getExpCuivreOncf()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getExpPbCmgOnf()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getExpZincOncf()));
        totalGlobal = totalGlobal.add(BigDecimal.valueOf(dto.getExpZincSafi()));

        dto.setGlobal(totalGlobal);

        return dto;
    }

    /**
     * Récupère les cumuls annuels regroupés.
     * Utilise la méthode 'sumAnnee' des repositories.
     *
     * @param annee L'année pour laquelle récupérer les cumuls.
     * @return Un objet SyntheseCumulAnnuelGroupesDTO.
     */
    // Dans SyntheseService.java, méthode getSyntheseCumulAnnuelGroupes(int annee)
    public SyntheseCumulAnnuelGroupesDTO getSyntheseCumulAnnuelGroupes(int annee) {
        LocalDate debutAnnee = LocalDate.of(annee, 1, 1);
        LocalDate finAnnee = LocalDate.of(annee, 12, 31);

        // Ajoutez des logs ici pour voir les valeurs retournées par chaque repository
        double dsClassiqueVal = Objects.requireNonNullElse(dsClassiqueRepository.sumAnnee(debutAnnee, finAnnee), 0.0);
        System.out.println("DEBUG: dsClassique sum for " + annee + ": " + dsClassiqueVal);

        double dsNordVal = Objects.requireNonNullElse(dsNordRepository.sumAnnee(debutAnnee, finAnnee), 0.0);
        System.out.println("DEBUG: dsNord sum for " + annee + ": " + dsNordVal);

        double kaVal = Objects.requireNonNullElse(controleBasculeKARepository.sumAnnee(debutAnnee, finAnnee), 0.0);
        System.out.println("DEBUG: controleBasculeKA sum for " + annee + ": " + kaVal);

        // ... continuez pour tous les autres repositories

        // Calcul des cumuls regroupés
        double cumulDsNordKa = dsClassiqueVal + dsNordVal + kaVal;
        // ... (le reste de vos calculs) ...

        SyntheseCumulAnnuelGroupesDTO dto = new SyntheseCumulAnnuelGroupesDTO();
        dto.setCUMUL_ANNEE_DS_NORD_KA(cumulDsNordKa);
        // ... (définissez les autres cumuls) ...

        return dto;
    }
}
