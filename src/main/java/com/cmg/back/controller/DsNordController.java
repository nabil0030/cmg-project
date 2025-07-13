package com.cmg.back.controller;

import com.cmg.back.model.DsNordRecord;
import com.cmg.back.service.DsNordExportService;
import com.cmg.back.service.DsNordRecordService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dsnord")
@CrossOrigin(origins = "*")
public class DsNordController {

    private final DsNordRecordService service;
    private final DsNordExportService exportService;

    @Autowired
    public DsNordController(DsNordRecordService service, DsNordExportService exportService) {
        this.service = service;
        this.exportService = exportService;
    }

    @GetMapping
    public List<DsNordRecord> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<DsNordRecord> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public String create(@RequestBody DsNordRecord record) {
        record.setNet(record.getTb() - record.getTare());
        if (service.isDuplicate(record)) {
            return "⚠️ Ligne dupliquée : cette entrée existe déjà.";
        }
        service.save(record);
        return "✅ Ligne ajoutée avec succès.";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody DsNordRecord record) {
        record.setId(id);
        record.setNet(record.getTb() - record.getTare());
        if (service.isDuplicate(record)) {
            return "⚠️ Ligne dupliquée : cette entrée existe déjà.";
        }
        service.save(record);
        return "✅ Ligne modifiée avec succès.";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }
}
