package com.cmg.back.export; // ✅ CORRIGÉ

import com.cmg.back.model.ControleBasculeHJDS;
import com.cmg.back.repository.ControleBasculeHJDSRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ControleBasculeHJDSExportService {

    private final ControleBasculeHJDSRepository repository;

    public ControleBasculeHJDSExportService(ControleBasculeHJDSRepository repository) {
        this.repository = repository;
    }

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ControleBasculeHJDS> list = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bascule HJ DS");

        Row header = sheet.createRow(0);
        String[] columns = {"Date", "Transporteur", "N° BL", "Immatricule", "Net DS", "Net CMG", "Écart", "Observation"};

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (ControleBasculeHJDS entry : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(entry.getDate().toString());
            row.createCell(1).setCellValue(entry.getTransporteur());
            row.createCell(2).setCellValue(entry.getNumeroBL());
            row.createCell(3).setCellValue(entry.getImmatricule());
            row.createCell(4).setCellValue(entry.getNetDS());
            row.createCell(5).setCellValue(entry.getNetCMG());
            row.createCell(6).setCellValue(entry.getEcart());
            row.createCell(7).setCellValue(entry.getObservation());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=controle_bascule_hjds.xlsx");

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
