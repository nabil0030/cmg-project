package com.cmg.back.controller;

import com.cmg.back.model.ExpPbCmgOnf;
import com.cmg.back.service.ExpPbCmgOnfService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/exp-pb-cmg-onf")
@CrossOrigin(origins = "*")
public class ExpPbCmgOnfController {
    @Autowired
    private ExpPbCmgOnfService service;

    @GetMapping
    public List<ExpPbCmgOnf> getAll() { return service.findAll(); }

    @PostMapping
    public ExpPbCmgOnf add(@RequestBody ExpPbCmgOnf entry) { return service.save(entry); }

    @PutMapping("/{id}")
    public ExpPbCmgOnf update(@PathVariable Long id, @RequestBody ExpPbCmgOnf entry) { return service.update(id, entry); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ExpPbCmgOnf> list = service.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("EXP PB CMG ONF");

        // Header
        Row header = sheet.createRow(0);
        String[] columns = {"DATE", "H entrée", "H sortie", "Transporteur", "N° BL", "MATRICULE", "TB", "TARE", "TN H", "N° CONTENAIRE", "Poste", "lieu de déchargement", "Observation"};
        for (int i = 0; i < columns.length; i++) header.createCell(i).setCellValue(columns[i]);

        int rowIdx = 1;
        for (ExpPbCmgOnf e : list) {
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
        response.setHeader("Content-Disposition", "attachment; filename=exp_pb_cmg_onf.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
