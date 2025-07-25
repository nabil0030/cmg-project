package com.cmg.back.controller; // Assurez-vous que le package est correct

import com.cmg.back.dto.SyntheseCumulAnnuelGroupesDTO; // Import ajouté/corrigé
import com.cmg.back.dto.SyntheseDailyDTO; // Import ajouté/corrigé
import com.cmg.back.dto.SyntheseDto; // Import corrigé pour votre nouvelle version
import com.cmg.back.service.SyntheseService;
import com.cmg.back.service.SyntheseExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SyntheseController {

    private final SyntheseService syntheseService;
    private final SyntheseExportService syntheseExportService;

    @Autowired
    public SyntheseController(SyntheseService syntheseService, SyntheseExportService syntheseExportService) {
        this.syntheseService = syntheseService;
        this.syntheseExportService = syntheseExportService;
    }

    @GetMapping("/synthese-jour")
    public SyntheseDailyDTO getSyntheseJour(@RequestParam String jour) {
        LocalDate date = LocalDate.parse(jour);
        return syntheseService.getDailySynthese(date);
    }

    @GetMapping("/synthese-mensuelle")
    public Map<String, List<Double>> getSyntheseMensuelle(@RequestParam(required = false) Integer annee) {
        int targetYear = (annee != null) ? annee : LocalDate.now().getYear();
        return syntheseService.getMonthlySummaryForExcelExport(targetYear);
    }

    @GetMapping("/synthese-annuelle")
    public SyntheseDto getSyntheseAnnee(@RequestParam(required = false) Integer annee) {
        int targetYear = (annee != null) ? annee : LocalDate.now().getYear();
        return syntheseService.getAnnualSynthese(targetYear);
    }

    @GetMapping("/synthese-cumul-annuel-groupes")
    public SyntheseCumulAnnuelGroupesDTO getSyntheseCumulAnnuelGroupes(@RequestParam(required = false) Integer annee) {
        int targetYear = (annee != null) ? annee : LocalDate.now().getYear();
        return syntheseService.getSyntheseCumulAnnuelGroupes(targetYear);
    }

    @GetMapping("/synthese/export")
    public void exportExcel(HttpServletResponse response, @RequestParam(required = false) Integer annee) throws IOException {
        int targetYear = (annee != null) ? annee : LocalDate.now().getYear();

        // Récupérer toutes les données nécessaires pour l'export Excel
        Map<String, List<Double>> monthlyData = syntheseService.getMonthlySummaryForExcelExport(targetYear);
        SyntheseDto annualDto = syntheseService.getAnnualSynthese(targetYear);
        SyntheseCumulAnnuelGroupesDTO groupedAnnualData = syntheseService.getSyntheseCumulAnnuelGroupes(targetYear);

        // Appeler le service d'exportation avec toutes les données
        syntheseExportService.exportSyntheseExcel(response, monthlyData, annualDto, groupedAnnualData);
    }
}
