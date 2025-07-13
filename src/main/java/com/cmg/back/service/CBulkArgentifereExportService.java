package com.cmg.back.export;

import com.cmg.back.model.CBulkArgentifere;
import com.cmg.back.repository.CBulkArgentifereRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CBulkArgentifereExportService {

    @Autowired
    private CBulkArgentifereRepository repository;

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<CBulkArgentifere> list = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("C BULK ARGENTIFERE");

        Row header = sheet.createRow(0);
        String[] columns = {"Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Matricule",
                "TB", "TARE", "TN H", "N° Contenaire", "Poste", "Lieu Déchargement", "Observation"};

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (CBulkArgentifere entry : list) {
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(entry.getDate());
            row.createCell(1).setCellValue(entry.getHeureEntree());
            row.createCell(2).setCellValue(entry.getHeureSortie());
            row.createCell(3).setCellValue(entry.getTransporteur());
            row.createCell(4).setCellValue(entry.getNumeroBL());
            row.createCell(5).setCellValue(entry.getMatricule());
            row.createCell(6).setCellValue(entry.getTb());
            row.createCell(7).setCellValue(entry.getTare());
            row.createCell(8).setCellValue(entry.getTnh());
            row.createCell(9).setCellValue(entry.getNumeroContenaire());
            row.createCell(10).setCellValue(entry.getPoste());
            row.createCell(11).setCellValue(entry.getLieuDechargement());
            row.createCell(12).setCellValue(entry.getObservation());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=cbulk_argentifere.xlsx");

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
