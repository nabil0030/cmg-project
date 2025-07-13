package com.cmg.back.service;

import com.cmg.back.model.DsNordRecord;
import com.cmg.back.repository.DsNordRecordRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DsNordExportService {

    private final DsNordRecordRepository repository;

    public DsNordExportService(DsNordRecordRepository repository) {
        this.repository = repository;
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<DsNordRecord> list = repository.findAll();

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("DS Nord");

        String[] cols = {
                "Date", "H Entrée", "H Sortie", "Transporteur", "N° BL",
                "Immatricule", "TB", "TARE", "NET", "Poste",
                "Lieu De Décharge", "Observation"
        };
        Row hdr = sheet.createRow(0);
        for (int i = 0; i < cols.length; i++) {
            hdr.createCell(i).setCellValue(cols[i]);
        }

        int rowIdx = 1;
        for (DsNordRecord r : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getDate()     != null ? r.getDate().toString()      : "");
            row.createCell(1).setCellValue(r.getHEntree()  != null ? r.getHEntree().toString()   : "");
            row.createCell(2).setCellValue(r.getHSortie()  != null ? r.getHSortie().toString()   : "");
            row.createCell(3).setCellValue(r.getTransporteur());
            row.createCell(4).setCellValue(r.getNumeroBL());
            row.createCell(5).setCellValue(r.getImmatricule());
            row.createCell(6).setCellValue(r.getTb()       != null ? r.getTb()                   : 0);
            row.createCell(7).setCellValue(r.getTare()     != null ? r.getTare()                 : 0);
            row.createCell(8).setCellValue(r.getNet()      != null ? r.getNet()                  : 0);
            row.createCell(9).setCellValue(r.getPoste());
            row.createCell(10).setCellValue(r.getLieuDeDecharge());
            row.createCell(11).setCellValue(r.getObservation());
        }

        for (int i = 0; i < cols.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String filename = "ds_nord_" + LocalDate.now() + ".xlsx";
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        wb.close();
        out.close();
    }
}
