package com.cmg.back.controller;

import com.cmg.back.model.DsClassique;
import com.cmg.back.repository.DsClassiqueRepository;
import com.cmg.back.service.DsClassiqueExportService;
import jakarta.servlet.http.HttpServletResponse;
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
    public String create(@RequestBody DsClassique record) {
        record.setNet(record.getTb() - record.getTare());
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
            return "⚠️ Ligne dupliquée : cette entrée existe déjà.";
        }
        repository.save(record);
        return "✅ Ligne ajoutée avec succès.";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody DsClassique record) {
        record.setId(id);
        record.setNet(record.getTb() - record.getTare());
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
            return "⚠️ Ligne dupliquée : cette entrée existe déjà.";
        }
        repository.save(record);
        return "✅ Ligne modifiée avec succès.";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }
}
