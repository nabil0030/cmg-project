package com.cmg.back.controller;

import com.cmg.back.model.ChauxViveLafarge;
import com.cmg.back.service.ChauxViveLafargeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chauxViveLafarge")
public class ChauxViveLafargeController {

    @Autowired
    private ChauxViveLafargeService service;

    @GetMapping
    public List<ChauxViveLafarge> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody ChauxViveLafarge entry) {
        return service.add(entry);
    }

    @PutMapping("/update")
    public ChauxViveLafarge update(@RequestBody ChauxViveLafarge entry) {
        return service.update(entry);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ✅ Export Excel : accessible via /api/chauxViveLafarge/export
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        service.exportToExcel(response);
    }
}
