package com.cmg.back.controller;

import com.cmg.back.model.DsClassique;
import com.cmg.back.repository.DsClassiqueRepository;
import com.cmg.back.service.DsClassiqueExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ds-classique")
@CrossOrigin(origins = "*")
public class DsClassiqueController {

    private final DsClassiqueRepository repository;
    private final DsClassiqueExportService exportService;

    public DsClassiqueController(DsClassiqueRepository repository,
                                 DsClassiqueExportService exportService) {
        this.repository = repository;
        this.exportService = exportService;
    }

    @GetMapping
    public List<DsClassique> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<DsClassique> getById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DsClassique record) {
        // Calcul automatique du champ net
        record.setNet(record.getTb() - record.getTare());

        // Vérifie si le N° BL existe déjà
        if (repository.existsByNumeroBL(record.getNumeroBL())) {
            return ResponseEntity.status(409).body("⚠️ Ce N° BL existe déjà.");
        }

        // Vérifie doublon complet
        boolean dup = repository.isDuplicate(
                record.getDate(),
                record.getHEntree(),
                record.getHSortie(),
                record.getTransporteur(),
                record.getNumeroBL(),
                record.getImmatricule(),
                record.getTb(),
                record.getTare(),
                record.getPoste(),
                record.getLieuDeDecharge(),
                record.getObservation()
        );

        if (dup) {
            return ResponseEntity.status(409).body("⚠️ Ligne dupliquée : cette entrée existe déjà.");
        }

        repository.save(record);
        return ResponseEntity.ok("✅ Ligne ajoutée avec succès.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody DsClassique record) {
        Optional<DsClassique> existing = repository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Calcul automatique du champ net
        record.setId(id);
        record.setNet(record.getTb() - record.getTare());

        // Vérifie si le nouveau numeroBL existe déjà (et appartient à un autre enregistrement)
        if (repository.existsByNumeroBL(record.getNumeroBL()) &&
                !existing.get().getNumeroBL().equals(record.getNumeroBL())) {
            return ResponseEntity.status(409).body("⚠️ Ce N° BL est déjà utilisé par un autre enregistrement.");
        }

        // Vérifie doublon complet
        boolean dup = repository.isDuplicate(
                record.getDate(),
                record.getHEntree(),
                record.getHSortie(),
                record.getTransporteur(),
                record.getNumeroBL(),
                record.getImmatricule(),
                record.getTb(),
                record.getTare(),
                record.getPoste(),
                record.getLieuDeDecharge(),
                record.getObservation()
        );

        if (dup) {
            return ResponseEntity.status(409).body("⚠️ Ligne dupliquée : cette entrée existe déjà.");
        }

        repository.save(record);
        return ResponseEntity.ok("✅ Ligne modifiée avec succès.");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

    @GetMapping("/bl/{numeroBL}")
    public ResponseEntity<DsClassique> getByNumeroBL(@PathVariable String numeroBL) {
        return repository.findByNumeroBL(numeroBL.trim())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/search/immatricule/{value}")
    public List<DsClassique> searchByImmatricule(@PathVariable String value) {
        return repository.findByImmatriculeContainingIgnoreCase(value);
    }

    @GetMapping("/search/transporteur/{value}")
    public List<DsClassique> searchByTransporteur(@PathVariable String value) {
        return repository.findByTransporteurContainingIgnoreCase(value);
    }


}
