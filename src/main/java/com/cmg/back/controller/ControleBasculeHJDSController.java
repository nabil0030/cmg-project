package com.cmg.back.controller;

import com.cmg.back.model.ControleBasculeHJDS;
import com.cmg.back.repository.ControleBasculeHJDSRepository;
import com.cmg.back.export.ControleBasculeHJDSExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bascule-hj-ds")
@CrossOrigin(origins = "*")
public class ControleBasculeHJDSController {

    @Autowired
    private ControleBasculeHJDSRepository repository;

    @Autowired
    private ControleBasculeHJDSExportService exportService;

    // ✅ Récupérer toutes les lignes triées par date décroissante
    @GetMapping
    public List<ControleBasculeHJDS> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    // ✅ Récupérer une ligne par ID
    @GetMapping("/{id}")
    public ResponseEntity<ControleBasculeHJDS> getById(@PathVariable Long id) {
        Optional<ControleBasculeHJDS> record = repository.findById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Ajouter une ligne (avec détection de duplicata)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ControleBasculeHJDS record) {
        // Calcul automatique du champ écart
        record.setEcart(record.getNetCmg() - record.getNetDs());

        boolean exists = repository.existsByDateAndTransporteurAndNumeroBLAndImmatriculeAndNetDsAndNetCmg(
                record.getDate(),
                record.getTransporteur(),
                record.getNumeroBL(),
                record.getImmatricule(),
                record.getNetDs(),
                record.getNetCmg()
        );

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Doublon détecté");
        }

        repository.save(record);
        return ResponseEntity.ok("Ajouté");
    }

    // ✅ Modifier une ligne
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ControleBasculeHJDS updated) {
        return repository.findById(id).map(record -> {
            record.setDate(updated.getDate());
            record.setTransporteur(updated.getTransporteur());
            record.setNumeroBL(updated.getNumeroBL());
            record.setImmatricule(updated.getImmatricule());
            record.setNetDs(updated.getNetDs());
            record.setNetCmg(updated.getNetCmg());
            record.setObservation(updated.getObservation());
            record.setEcart(updated.getNetCmg() - updated.getNetDs());

            repository.save(record);
            return ResponseEntity.ok("Modifié");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Supprimer une ligne
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Supprimé");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Exporter en Excel
    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }
}
