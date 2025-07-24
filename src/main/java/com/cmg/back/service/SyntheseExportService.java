package com.cmg.back.service;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SyntheseExportService {

    public void exportSyntheseExcel(HttpServletResponse response, Map<String, List<Double>> data) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Synthèse");

        // Styles
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setBold(true);
        headerStyle.setFont(fontBold);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle entreesStyle = workbook.createCellStyle();
        entreesStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
        entreesStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle expStyle = workbook.createCellStyle();
        expStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        expStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle totalStyle = workbook.createCellStyle();
        totalStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalStyle.setFont(fontBold);

        // En-têtes
        Row header1 = sheet.createRow(0);
        Row header2 = sheet.createRow(1);
        String[] moisLabels = {
                "Mois", "DRAA SFAR", "DRAA NORD", "KOUDIAT AÏCHA", "COSUMAR", "LAFARGE", "Total Entrée",
                "CU MRK", "CU NORD", "PB MRK", "PB CASA", "ZN ONCF", "ZN PORT", "ZN VRAC", "ZN CBULK", "Total Expédition", "Total Mensuel"
        };

        for (int i = 0; i < moisLabels.length; i++) {
            Cell cell = header2.createCell(i);
            cell.setCellValue(moisLabels[i]);
            cell.setCellStyle(headerStyle);
        }

        // Données
        int rowIndex = 2;
        for (String mois : data.keySet()) {
            Row row = sheet.createRow(rowIndex++);
            List<Double> values = data.get(mois);
            row.createCell(0).setCellValue(mois);

            double totalMois = 0;
            for (int i = 0; i < values.size(); i++) {
                double val = values.get(i);
                Cell cell = row.createCell(i + 1);
                cell.setCellValue(val);

                if (i < 6) cell.setCellStyle(entreesStyle);
                else if (i < 15) cell.setCellStyle(expStyle);

                totalMois += val;
            }
            row.createCell(16).setCellValue(totalMois);
            row.getCell(16).setCellStyle(totalStyle);
        }

        // Finalisation
        for (int i = 0; i < 17; i++) sheet.autoSizeColumn(i);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=synthese.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
