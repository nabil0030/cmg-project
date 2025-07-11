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

    public DsClassiqueController(DsClassiqueRepository repository, DsClassiqueExportService exportService) {
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
    public DsClassique create(@RequestBody DsClassique record) {
        return repository.save(record);
    }

    @PutMapping("/{id}")
    public DsClassique update(@PathVariable Long id, @RequestBody DsClassique record) {
        record.setId(id);
        return repository.save(record);
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
