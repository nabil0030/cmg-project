package com.cmg.back.controller;

import com.cmg.back.model.ChauxVive;
import com.cmg.back.repository.ChauxViveRepository;
import com.cmg.back.service.ChauxViveExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ChauxViveController {

    @Autowired
    private ChauxViveRepository chauxViveRepository;

    @Autowired
    private ChauxViveExportService exportService;

    // 1. Affichage page HTML
    @GetMapping("/chauxVive")
    public String afficherPage() {
        return "chauxVive"; // fichier resources/static/chauxVive.html
    }

    // 2. Ajout ou modification
    @PostMapping("/chauxVive/add")
    public String ajouterChauxVive(ChauxVive chauxVive) {
        chauxViveRepository.save(chauxVive);
        return "redirect:/chauxVive";
    }

    @PostMapping("/chauxVive/update")
    public String modifierChauxVive(ChauxVive chauxVive) {
        chauxViveRepository.save(chauxVive);
        return "redirect:/chauxVive";
    }

    // 3. Suppression
    @GetMapping("/chauxVive/delete/{id}")
    public String supprimerChauxVive(@PathVariable Long id) {
        chauxViveRepository.deleteById(id);
        return "redirect:/chauxVive";
    }

    // 4. Export Excel
    @GetMapping("/export/chauxVive/excel")
    public void exporterExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

    // 5. API REST : GET all
    @ResponseBody
    @GetMapping("/api/chauxVive")
    public List<ChauxVive> getAllChauxVive() {
        return chauxViveRepository.findAll();
    }

    // 6. API REST : GET by ID
    @ResponseBody
    @GetMapping("/api/chauxVive/{id}")
    public ChauxVive getChauxViveById(@PathVariable Long id) {
        Optional<ChauxVive> result = chauxViveRepository.findById(id);
        return result.orElse(null);
    }
}
