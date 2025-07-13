package com.cmg.back.service;

import com.cmg.back.model.ExpZincOncf;
import com.cmg.back.repository.ExpZincOncfRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpZincOncfExportService {

    private final ExpZincOncfRepository repository;

    public ExpZincOncfExportService(ExpZincOncfRepository repository) {
        this.repository = repository;
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ExpZincOncf> list = repository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Export Zinc ONCF");

        String[] columns = {
                "Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Matricule",
                "TB", "TARE", "TN H", "N° Contenaire", "Poste", "Lieu de Déchargement", "Observation"
        };

        Row header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (ExpZincOncf e : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(e.getDate());
            row.createCell(1).setCellValue(e.getHeureEntree().toString());
            row.createCell(2).setCellValue(e.getHeureSortie().toString());
            row.createCell(3).setCellValue(e.getTransporteur());
            row.createCell(4).setCellValue(e.getNumeroBL());
            row.createCell(5).setCellValue(e.getMatricule());
            row.createCell(6).setCellValue(e.getTb());
            row.createCell(7).setCellValue(e.getTare());
            row.createCell(8).setCellValue(e.getTnH());
            row.createCell(9).setCellValue(e.getNumeroContenaire());
            row.createCell(10).setCellValue(e.getPoste());
            row.createCell(11).setCellValue(e.getLieuDeDechargement());
            row.createCell(12).setCellValue(e.getObservation());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String filename = "export_zinc_oncf_" + LocalDate.now() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
