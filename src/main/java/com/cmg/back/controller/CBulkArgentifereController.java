package com.cmg.back.controller;

import com.cmg.back.model.CBulkArgentifere;
import com.cmg.back.service.CBulkArgentifereService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cbulkargentifere")
public class CBulkArgentifereController {

    @Autowired
    private CBulkArgentifereService service;

    @GetMapping
    public List<CBulkArgentifere> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody CBulkArgentifere entry) {
        return service.add(entry);
    }

    @PutMapping("/update")
    public CBulkArgentifere update(@RequestBody CBulkArgentifere entry) {
        return service.update(entry);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @Autowired
    private com.cmg.back.export.CBulkArgentifereExportService exportService;

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

}
