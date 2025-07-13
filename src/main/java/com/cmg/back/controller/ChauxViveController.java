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
    private ChauxViveRepository repository;

    @Autowired
    private ChauxViveExportService exportService;

    // 1. ✅ Affichage de la page HTML
    @GetMapping("/chauxVive")
    public String afficherPage() {
        return "chauxVive";
    }

    // 2. ✅ API - Obtenir tous les enregistrements
    @GetMapping("/api/chauxVive")
    @ResponseBody
    public List<ChauxVive> getAll() {
        return repository.findAll();
    }

    // 3. ✅ API - Obtenir une ligne par ID
    @GetMapping("/api/chauxVive/{id}")
    @ResponseBody
    public Optional<ChauxVive> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    // 4. ✅ API - Ajouter une entrée
    @PostMapping("/chauxVive/add")
    @ResponseBody
    public String ajouter(ChauxVive chauxVive) {
        chauxVive.setNetCmg(chauxVive.getTb() - chauxVive.getTare());
        chauxVive.setEcart(chauxVive.getNetCmg() - chauxVive.getNetCosumar());

        boolean exists = repository.isDuplicate(
                chauxVive.getDate(), chauxVive.getHEntree(), chauxVive.getHSortie(),
                chauxVive.getTransporteur(), chauxVive.getNumeroBL(), chauxVive.getImmatricule(),
                chauxVive.getTb(), chauxVive.getTare(), chauxVive.getPoste(),
                chauxVive.getLieuChargement(), chauxVive.getLieuDechargement()
        );

        if (exists && chauxVive.getId() == null) {
            throw new RuntimeException("Doublon détecté");
        }

        repository.save(chauxVive);
        return "ok";
    }

    // 5. ✅ API - Modifier une entrée
    @PostMapping("/chauxVive/update")
    @ResponseBody
    public String modifier(ChauxVive chauxVive) {
        return ajouter(chauxVive); // même logique
    }

    // 6. ✅ API - Supprimer une entrée
    @GetMapping("/chauxVive/delete/{id}")
    @ResponseBody
    public void supprimer(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // 7. ✅ Export Excel
    @GetMapping("/chauxVive/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }
}
