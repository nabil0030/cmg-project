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
        List<ChauxVive> data = repository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chaux Vive");

        Row header = sheet.createRow(0);
        String[] columns = {
                "Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Immatricule",
                "TB", "TARE", "NET CMG", "Poste", "Net COSUMAR", "ÉCART",
                "Lieu Chargement", "Lieu Déchargement", "Observation"
        };
        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int rowIndex = 1;
        for (ChauxVive c : data) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(c.getDate());
            row.createCell(1).setCellValue(c.getHEntree());
            row.createCell(2).setCellValue(c.getHSortie());
            row.createCell(3).setCellValue(c.getTransporteur());
            row.createCell(4).setCellValue(c.getNumeroBL());
            row.createCell(5).setCellValue(c.getImmatricule());
            row.createCell(6).setCellValue(c.getTb());
            row.createCell(7).setCellValue(c.getTare());
            row.createCell(8).setCellValue(c.getNetCMG());
            row.createCell(9).setCellValue(c.getPoste());
            row.createCell(10).setCellValue(c.getNetCosumar());
            row.createCell(11).setCellValue(c.getEcart());
            row.createCell(12).setCellValue(c.getLieuChargement());
            row.createCell(13).setCellValue(c.getLieuDechargement());
            row.createCell(14).setCellValue(c.getObservation());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerValue = "attachment; filename=chaux_vive.xlsx";
        response.setHeader("Content-Disposition", headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
