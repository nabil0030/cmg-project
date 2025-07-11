package com.cmg.back.service;

import com.cmg.back.model.DsNordRecord;
import com.cmg.back.repository.DsNordRecordRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DsNordExportService {

    private final DsNordRecordRepository repository;

    public DsNordExportService(DsNordRecordRepository repository) {
        this.repository = repository;
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<DsNordRecord> records = repository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DS NORD");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Immatricule", "TB", "TARE", "NET", "Poste", "Lieu Décharge", "Observation"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (DsNordRecord record : records) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(record.getDate() != null ? record.getDate().toString() : "");
                row.createCell(1).setCellValue(record.gethEntree() != null ? record.gethEntree().toString() : "");
                row.createCell(2).setCellValue(record.gethSortie() != null ? record.gethSortie().toString() : "");
                row.createCell(3).setCellValue(record.getTransporteur());
                row.createCell(4).setCellValue(record.getNumeroBL());
                row.createCell(5).setCellValue(record.getImmatricule());
                row.createCell(6).setCellValue(record.getTb() != null ? record.getTb() : 0);
                row.createCell(7).setCellValue(record.getTare() != null ? record.getTare() : 0);
                row.createCell(8).setCellValue(record.getNet() != null ? record.getNet() : 0);
                row.createCell(9).setCellValue(record.getPoste());
                row.createCell(10).setCellValue(record.getLieuDeDecharge());
                row.createCell(11).setCellValue(record.getObservation());
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=dsnord.xlsx");
            workbook.write(response.getOutputStream());
        }
    }
}
