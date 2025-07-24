package com.cmg.back.controller;

import com.cmg.back.repository.*;
import com.cmg.back.service.SyntheseExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SyntheseController {

    private final DsClassiqueRepository dsClassiqueRepository;
    private final DsNordRecordRepository dsNordRepository;
    private final ChauxViveLafargeRepository chauxViveLafargeRepository;
    private final CBulkArgentifereRepository cBulkArgentifereRepository;
    private final ChauxViveRepository chauxViveRepository;
    private final ControleBasculeHJDSRepository controleBasculeHJDSRepository;
    private final ControleBasculeKARepository controleBasculeKARepository;
    private final ExpCuivreNordRepository expCuivreNordRepository;
    private final ExpCuivreOncfRepository expCuivreOncfRepository;
    private final ExpPbCmgOnfRepository expPbCmgOnfRepository;
    private final ExpZincOncfRepository expZincOncfRepository;
    private final ExpZincSafiRepository expZincSafiRepository;

    public SyntheseController(
            DsClassiqueRepository dsClassiqueRepository,
            DsNordRecordRepository dsNordRepository,
            ChauxViveLafargeRepository chauxViveLafargeRepository,
            CBulkArgentifereRepository cBulkArgentifereRepository,
            ChauxViveRepository chauxViveRepository,
            ControleBasculeHJDSRepository controleBasculeHJDSRepository,
            ControleBasculeKARepository controleBasculeKARepository,
            ExpCuivreNordRepository expCuivreNordRepository,
            ExpCuivreOncfRepository expCuivreOncfRepository,
            ExpPbCmgOnfRepository expPbCmgOnfRepository,
            ExpZincOncfRepository expZincOncfRepository,
            ExpZincSafiRepository expZincSafiRepository
    ) {
        this.dsClassiqueRepository = dsClassiqueRepository;
        this.dsNordRepository = dsNordRepository;
        this.chauxViveLafargeRepository = chauxViveLafargeRepository;
        this.cBulkArgentifereRepository = cBulkArgentifereRepository;
        this.chauxViveRepository = chauxViveRepository;
        this.controleBasculeHJDSRepository = controleBasculeHJDSRepository;
        this.controleBasculeKARepository = controleBasculeKARepository;
        this.expCuivreNordRepository = expCuivreNordRepository;
        this.expCuivreOncfRepository = expCuivreOncfRepository;
        this.expPbCmgOnfRepository = expPbCmgOnfRepository;
        this.expZincOncfRepository = expZincOncfRepository;
        this.expZincSafiRepository = expZincSafiRepository;
    }

    @GetMapping("/synthese-jour")
    public Map<String, Object> getJour(@RequestParam LocalDate jour) {
        Map<String, Object> map = new LinkedHashMap<>();
        double total = 0;

        total += add(map, "dsClassique", dsClassiqueRepository.sumJour(jour));
        total += add(map, "dsNord", dsNordRepository.sumJour(jour));
        total += add(map, "chauxViveLafarge", chauxViveLafargeRepository.sumJour(jour));
        total += add(map, "cbulkArgentifere", cBulkArgentifereRepository.sumJour(jour));
        total += add(map, "chauxVive", chauxViveRepository.sumJour(jour));
        total += add(map, "controleBasculeHJDS", controleBasculeHJDSRepository.sumJour(jour));
        total += add(map, "controleBasculeKA", controleBasculeKARepository.sumJour(jour));
        total += add(map, "expCuivreNord", expCuivreNordRepository.sumJour(jour));
        total += add(map, "expCuivreOncf", expCuivreOncfRepository.sumJour(jour));
        total += add(map, "expPbCmgOnf", expPbCmgOnfRepository.sumJour(jour));
        total += add(map, "expZincOncf", expZincOncfRepository.sumJour(jour));
        total += add(map, "expZincSafi", expZincSafiRepository.sumJour(jour));

        map.put("global", total);
        return map;
    }

    @GetMapping("/synthese-mois")
    public Map<String, Object> getMois(@RequestParam String mois) {
        YearMonth ym = YearMonth.parse(mois);
        LocalDate debut = ym.atDay(1);
        LocalDate fin = ym.atEndOfMonth();
        return getSyntheseEntre(debut, fin);
    }

    @GetMapping("/synthese-annuelle")
    public Map<String, Object> getAnnee(@RequestParam int annee) {
        LocalDate debut = LocalDate.of(annee, 1, 1);
        LocalDate fin = LocalDate.of(annee, 12, 31);
        return getSyntheseEntre(debut, fin);
    }

    private Map<String, Object> getSyntheseEntre(LocalDate debut, LocalDate fin) {
        Map<String, Object> map = new LinkedHashMap<>();
        double total = 0;

        total += add(map, "dsClassique", dsClassiqueRepository.sumForMonth(debut, fin));
        total += add(map, "dsNord", dsNordRepository.sumForMonth(debut, fin));
        total += add(map, "chauxViveLafarge", chauxViveLafargeRepository.sumForMonth(debut, fin));
        total += add(map, "cbulkArgentifere", cBulkArgentifereRepository.sumForMonth(debut, fin));
        total += add(map, "chauxVive", chauxViveRepository.sumForMonth(debut, fin));
        total += add(map, "controleBasculeHJDS", controleBasculeHJDSRepository.sumForMonth(debut, fin));
        total += add(map, "controleBasculeKA", controleBasculeKARepository.sumForMonth(debut, fin));
        total += add(map, "expCuivreNord", expCuivreNordRepository.sumForMonth(debut, fin));
        total += add(map, "expCuivreOncf", expCuivreOncfRepository.sumForMonth(debut, fin));
        total += add(map, "expPbCmgOnf", expPbCmgOnfRepository.sumForMonth(debut, fin));
        total += add(map, "expZincOncf", expZincOncfRepository.sumForMonth(debut, fin));
        total += add(map, "expZincSafi", expZincSafiRepository.sumForMonth(debut, fin));

        map.put("global", total);
        return map;
    }

    private double add(Map<String, Object> map, String label, double value) {
        map.put(label, value);
        return value;
    }
    @GetMapping("/synthese-mensuelle")
    public Map<String, List<Double>> getSyntheseMensuelle() {
        Map<String, List<Double>> result = new LinkedHashMap<>();

        for (int mois = 1; mois <= 12; mois++) {
            YearMonth ym = YearMonth.of(LocalDate.now().getYear(), mois);
            LocalDate debut = ym.atDay(1);
            LocalDate fin = ym.atEndOfMonth();

            List<Double> ligne = new ArrayList<>();

            // LES ENTRÉES (6 colonnes)
            ligne.add(dsClassiqueRepository.sumForMonth(debut, fin)); // DRAA SFAR
            ligne.add(dsNordRepository.sumForMonth(debut, fin));      // DRAA NORD
            ligne.add(controleBasculeKARepository.sumForMonth(debut, fin)); // KOUDIAT AÏCHA
            ligne.add(chauxViveRepository.sumForMonth(debut, fin));   // CHAUX COSUMAR
            ligne.add(chauxViveLafargeRepository.sumForMonth(debut, fin)); // CHAUX LAFARGE
            ligne.add(ligne.get(0) + ligne.get(1) + ligne.get(2) + ligne.get(3) + ligne.get(4)); // Total Entrée

            // LES EXPÉDITIONS (9 colonnes)
            ligne.add(expCuivreOncfRepository.sumForMonth(debut, fin));     // CUIVRE ONCF MRK
            ligne.add(expCuivreNordRepository.sumForMonth(debut, fin));     // CUIVRE NORD
            ligne.add(expPbCmgOnfRepository.sumForMonth(debut, fin));       // PLOMB ONCF MRK
            ligne.add(cBulkArgentifereRepository.sumForMonth(debut, fin));  // PLOMB PORT CASA
            ligne.add(expZincOncfRepository.sumForMonth(debut, fin));       // ZINC ONCF
            ligne.add(0.0);                                                 // ZINC PORT CASA (non utilisé)
            ligne.add(0.0);                                                 // ZINC EN VRAC (non utilisé)
            ligne.add(expZincSafiRepository.sumForMonth(debut, fin));       // ZINC CBULK
            double totalExp = 0;
            for (int i = 6; i <= 13; i++) totalExp += ligne.get(i);
            ligne.add(totalExp); // Total Expéditions

            // Ajouter au résultat
            String nomMois = ym.getMonth().name(); // "JANUARY"
            String nomFormate = nomMois.charAt(0) + nomMois.substring(1).toLowerCase(); // January
            result.put(nomFormate.toUpperCase(), ligne); // "JANVIER" etc.
        }

        return result;
    }
    @GetMapping("/synthese/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        Map<String, List<Double>> data = getSyntheseMensuelle();
        new SyntheseExportService().exportSyntheseExcel(response, data);
    }


}
