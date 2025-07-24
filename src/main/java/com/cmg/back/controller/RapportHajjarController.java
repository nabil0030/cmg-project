package com.cmg.back.controller;

import com.cmg.back.dto.RapportHajjarDto;
import com.cmg.back.service.RapportHajjarService;
import com.cmg.back.export.RapportHajjarExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rapport-hajjar")
public class RapportHajjarController {

    @Autowired
    private RapportHajjarService service;

    @Autowired
    private RapportHajjarExportService exportService;

    @GetMapping
    public List<RapportHajjarDto> getRapport(@RequestParam LocalDate date) {
        return service.genererRapport(date);
    }

    @GetMapping("/export")
    public void exportExcel(@RequestParam LocalDate date, HttpServletResponse response) throws IOException {
        exportService.exportRapportHajjar(date, response);
    }
}
