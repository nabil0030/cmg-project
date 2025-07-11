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

    @Autowired
    private DsNordRecordService service;

    @GetMapping
    public List<DsNordRecord> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<DsNordRecord> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public DsNordRecord create(@RequestBody DsNordRecord record) {
        return service.save(record);
    }
    @Autowired
    private DsNordExportService exportService;

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }


    @PutMapping("/{id}")
    public DsNordRecord update(@PathVariable Long id, @RequestBody DsNordRecord record) {
        record.setId(id);
        return service.save(record);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
