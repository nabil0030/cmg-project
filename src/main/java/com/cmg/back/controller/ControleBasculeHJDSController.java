package com.cmg.back.controller;

import com.cmg.back.model.ControleBasculeHJDS;
import com.cmg.back.model.DsClassique;
import com.cmg.back.repository.ControleBasculeHJDSRepository;
import com.cmg.back.repository.DsClassiqueRepository;
import com.cmg.back.export.ControleBasculeHJDSExportService;
import com.cmg.back.service.ControleBasculeHJDSService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bascule-hj-ds")
@CrossOrigin(origins = "*")
public class ControleBasculeHJDSController {

    private final ControleBasculeHJDSRepository controleRepo;
    private final DsClassiqueRepository dsRepo;
    private final ControleBasculeHJDSService service;
    private final ControleBasculeHJDSExportService exportService;

    public ControleBasculeHJDSController(
            ControleBasculeHJDSRepository controleRepo,
            DsClassiqueRepository dsRepo,
            ControleBasculeHJDSService service,
            ControleBasculeHJDSExportService exportService
    ) {
        this.controleRepo = controleRepo;
        this.dsRepo = dsRepo;
        this.service = service;
        this.exportService = exportService;
    }

    @GetMapping
    public List<ControleBasculeHJDS> getAll() {
        return controleRepo.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ControleBasculeHJDS> getById(@PathVariable Long id) {
        return controleRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ControleBasculeHJDS record) {
        String cleanedBL = record.getNumeroBL().replaceAll("\\s+", "").trim();
        Optional<DsClassique> dsOpt = dsRepo.findByNumeroBL(cleanedBL);
        record.setNumeroBL(cleanedBL); // Met à jour le champ nettoyé dans l'objet
        if (dsOpt.isEmpty()) {
            return ResponseEntity.status(404).body("❌ N° BL introuvable dans DS CLASSIQUE");
        }
        if (controleRepo.existsByNumeroBL(record.getNumeroBL())) {
            return ResponseEntity.status(409).body("⚠️ Ce N° BL existe déjà.");
        }


        DsClassique ds = dsOpt.get();
        record.setDate(ds.getDate());
        record.setTransporteur(ds.getTransporteur());
        record.setImmatricule(ds.getImmatricule());
        record.setNetDs(ds.getNet());
        record.setEcart(ds.getNet() - record.getNetCmg());

        boolean duplicate = controleRepo.isDuplicate(
                record.getDate(),
                record.getTransporteur(),
                record.getNumeroBL(),
                record.getImmatricule(),
                record.getNetCmg(),
                record.getObservation()
        );

        if (duplicate) {
            return ResponseEntity.status(409).body("⚠️ Doublon détecté : cette ligne existe déjà.");
        }

        controleRepo.save(record);
        return ResponseEntity.ok("✅ Enregistrement ajouté.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ControleBasculeHJDS updated) {
        return controleRepo.findById(id).map(record -> {
            record.setDate(updated.getDate());
            record.setTransporteur(updated.getTransporteur());
            record.setNumeroBL(updated.getNumeroBL());
            record.setImmatricule(updated.getImmatricule());
            record.setNetCmg(updated.getNetCmg());
            record.setObservation(updated.getObservation());
            service.save(record); // ⬅ recalcul netDs + écart
            return ResponseEntity.ok("✅ Modifié");
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (controleRepo.existsById(id)) {
            controleRepo.deleteById(id);
            return ResponseEntity.ok("✅ Supprimé");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

}
