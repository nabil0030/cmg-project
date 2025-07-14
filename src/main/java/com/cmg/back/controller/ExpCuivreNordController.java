package com.cmg.back.controller;

import com.cmg.back.model.ExpCuivreNord;
import com.cmg.back.service.ExpCuivreNordService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/exp-cuivre-nord")
@CrossOrigin(origins = "*")
public class ExpCuivreNordController {
    @Autowired
    private ExpCuivreNordService service;

    @GetMapping
    public List<ExpCuivreNord> getAll() { return service.findAll(); }

    @PostMapping
    public ExpCuivreNord add(@RequestBody ExpCuivreNord entry) { return service.save(entry); }

    @PutMapping("/{id}")
    public ExpCuivreNord update(@PathVariable Long id, @RequestBody ExpCuivreNord entry) { return service.update(id, entry); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ExpCuivreNord> list = service.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("EXP C CUIVRE NORD");

        // Header
        Row header = sheet.createRow(0);
        String[] columns = {"DATE", "H entrée", "H sortie", "Transporteur", "N° BL", "MATRICULE", "TB", "TARE", "TN H", "N° CONTENAIRE", "Poste", "lieu de déchargement", "Observation"};
        for (int i = 0; i < columns.length; i++) header.createCell(i).setCellValue(columns[i]);

        int rowIdx = 1;
        for (ExpCuivreNord e : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getDate() != null ? e.getDate().toString() : "");
            row.createCell(1).setCellValue(e.getHEntree() != null ? e.getHEntree().toString() : "");
            row.createCell(2).setCellValue(e.getHSortie() != null ? e.getHSortie().toString() : "");
            row.createCell(3).setCellValue(e.getTransporteur());
            row.createCell(4).setCellValue(e.getNumeroBL());
            row.createCell(5).setCellValue(e.getMatricule());
            row.createCell(6).setCellValue(e.getTb() != null ? e.getTb() : 0.0);
            row.createCell(7).setCellValue(e.getTare() != null ? e.getTare() : 0.0);
            row.createCell(8).setCellValue(e.getTnH() != null ? e.getTnH() : 0.0);
            row.createCell(9).setCellValue(e.getNumeroContenaire());
            row.createCell(10).setCellValue(e.getPoste() != null ? e.getPoste() : 0);
            row.createCell(11).setCellValue(e.getLieuDeDechargement());
            row.createCell(12).setCellValue(e.getObservation());
        }
        for (int i = 0; i < columns.length; i++) sheet.autoSizeColumn(i);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=exp_cuivre_nord.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
