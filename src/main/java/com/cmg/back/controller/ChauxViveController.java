package com.cmg.back.controller;

import com.cmg.back.model.ChauxVive;
import com.cmg.back.service.ChauxViveService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chaux-vive")
@CrossOrigin(origins = "*")
public class ChauxViveController {

    @Autowired
    private ChauxViveService service;

    @GetMapping
    public List<ChauxVive> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ChauxVive add(@RequestBody ChauxVive entry) {
        return service.save(entry);
    }

    @PutMapping("/{id}")
    public ChauxVive update(@PathVariable Long id, @RequestBody ChauxVive entry) {
        return service.update(id, entry);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ChauxVive> list = service.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("CHAUX VIVE");
        CellStyle decimalStyle = workbook.createCellStyle();
        decimalStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

        // Header
        Row header = sheet.createRow(0);
        String[] columns = {"DATE", "H entrée", "H sortie", "Transporteur", "N° BL", "IMMAT", "TB", "TARE", "NET CMG", "Poste", "Net COSUMAR", "ECART", "Lieu de chargement", "Lieu de déchargement", "Observation"};
        for (int i = 0; i < columns.length; i++) header.createCell(i).setCellValue(columns[i]);

        int rowIdx = 1;
        for (ChauxVive e : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getDate() != null ? e.getDate().toString() : "");
            row.createCell(1).setCellValue(e.getHEntree() != null ? e.getHEntree().toString() : "");
            row.createCell(2).setCellValue(e.getHSortie() != null ? e.getHSortie().toString() : "");
            row.createCell(3).setCellValue(e.getTransporteur());
            row.createCell(4).setCellValue(e.getNumeroBL());
            row.createCell(5).setCellValue(e.getImmat());

            // Cellules numériques formatées
            Cell cell6 = row.createCell(6); cell6.setCellValue(e.getTb() != null ? round2(e.getTb()) : 0.0); cell6.setCellStyle(decimalStyle);
            Cell cell7 = row.createCell(7); cell7.setCellValue(e.getTare() != null ? round2(e.getTare()) : 0.0); cell7.setCellStyle(decimalStyle);
            Cell cell8 = row.createCell(8); cell8.setCellValue(e.getNetCmg() != null ? round2(e.getNetCmg()) : 0.0); cell8.setCellStyle(decimalStyle);
            row.createCell(9).setCellValue(e.getPoste() != null ? e.getPoste() : 0);
            Cell cell10 = row.createCell(10); cell10.setCellValue(e.getNetCosumar() != null ? round2(e.getNetCosumar()) : 0.0); cell10.setCellStyle(decimalStyle);
            Cell cell11 = row.createCell(11); cell11.setCellValue(e.getEcart() != null ? round2(e.getEcart()) : 0.0); cell11.setCellStyle(decimalStyle);

            row.createCell(12).setCellValue(e.getLieuChargement());
            row.createCell(13).setCellValue(e.getLieuDechargement());
            row.createCell(14).setCellValue(e.getObservation());
        }
        for (int i = 0; i < columns.length; i++) sheet.autoSizeColumn(i);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=chaux_vive.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private double round2(Double val) {
        if(val == null) return 0.0;
        return Math.round(val * 100.0) / 100.0;
    }

    @GetMapping("/compte")
    public Map<String, Double> getComptes() {
        List<ChauxVive> list = service.findAll();
        double totalNetCmg = 0.0;
        double totalNetCosumar = 0.0;
        double totalEcart = 0.0;

        for (ChauxVive c : list) {
            totalNetCmg += c.getNetCmg() != null ? c.getNetCmg() : 0.0;
            totalNetCosumar += c.getNetCosumar() != null ? c.getNetCosumar() : 0.0;
            totalEcart += c.getEcart() != null ? c.getEcart() : 0.0;
        }

        Map<String, Double> res = new HashMap<>();
        res.put("totalNetCmg", round2(totalNetCmg));
        res.put("totalNetCosumar", round2(totalNetCosumar));
        res.put("totalEcart", round2(totalEcart));
        return res;
    }
}
