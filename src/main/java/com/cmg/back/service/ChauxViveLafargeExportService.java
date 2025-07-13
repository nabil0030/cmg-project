package com.cmg.back.service;

import com.cmg.back.model.ChauxViveLafarge;
import com.cmg.back.repository.ChauxViveLafargeRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ChauxViveLafargeExportService {

    @Autowired
    private ChauxViveLafargeRepository repository;

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ChauxViveLafarge> list = repository.findAllByOrderByDateAsc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chaux Vive Lafarge");

        Row header = sheet.createRow(0);
        String[] columns = {"Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Immatricule",
                "TB", "TARE", "NET CMG", "Poste", "Net LAFARGE", "ÉCART",
                "Chargement", "Déchargement", "Observation"};

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowNum = 1;
        for (ChauxViveLafarge item : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getDate());
            row.createCell(1).setCellValue(item.getHEntree());
            row.createCell(2).setCellValue(item.getHSortie());
            row.createCell(3).setCellValue(item.getTransporteur());
            row.createCell(4).setCellValue(item.getNumeroBL());
            row.createCell(5).setCellValue(item.getImmatricule());
            row.createCell(6).setCellValue(item.getTb());
            row.createCell(7).setCellValue(item.getTare());
            row.createCell(8).setCellValue(item.getNetCMG());
            row.createCell(9).setCellValue(item.getPoste());
            row.createCell(10).setCellValue(item.getNetLafarge());
            row.createCell(11).setCellValue(item.getEcart());
            row.createCell(12).setCellValue(item.getLieuChargement());
            row.createCell(13).setCellValue(item.getLieuDechargement());
            row.createCell(14).setCellValue(item.getObservation() != null ? item.getObservation() : "");
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=chaux_vive_lafarge.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
