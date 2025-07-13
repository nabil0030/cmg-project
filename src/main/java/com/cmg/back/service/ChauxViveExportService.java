package com.cmg.back.service;

import com.cmg.back.model.ChauxVive;
import com.cmg.back.repository.ChauxViveRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ChauxViveExportService {

    private final ChauxViveRepository repository;

    public ChauxViveExportService(ChauxViveRepository repository) {
        this.repository = repository;
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ChauxVive> list = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chaux Vive");

        // Header
        Row header = sheet.createRow(0);
        String[] columns = {
                "Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Immatricule",
                "TB", "TARE", "NET CMG", "NET COSUMAR", "ÉCART",
                "Poste", "Lieu Chargement", "Lieu Déchargement", "Observation"
        };
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Data
        int rowNum = 1;
        for (ChauxVive cv : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cv.getDate());
            row.createCell(1).setCellValue(cv.getHEntree().toString());
            row.createCell(2).setCellValue(cv.getHSortie().toString());
            row.createCell(3).setCellValue(cv.getTransporteur());
            row.createCell(4).setCellValue(cv.getNumeroBL());
            row.createCell(5).setCellValue(cv.getImmatricule());
            row.createCell(6).setCellValue(cv.getTb());
            row.createCell(7).setCellValue(cv.getTare());
            row.createCell(8).setCellValue(cv.getNetCmg());
            row.createCell(9).setCellValue(cv.getNetCosumar());
            row.createCell(10).setCellValue(cv.getEcart());
            row.createCell(11).setCellValue(cv.getPoste());
            row.createCell(12).setCellValue(cv.getLieuChargement());
            row.createCell(13).setCellValue(cv.getLieuDechargement());
            row.createCell(14).setCellValue(cv.getObservation());
        }

        // Auto-size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=chaux_vive.xlsx");

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
