package com.cmg.back.export;

import com.cmg.back.dto.RapportHajjarDto;
import com.cmg.back.service.RapportHajjarService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RapportHajjarExportService {

    private final RapportHajjarService service;

    public RapportHajjarExportService(RapportHajjarService service) {
        this.service = service;
    }

    public void exportRapportHajjar(LocalDate date, HttpServletResponse response) throws IOException {
        List<RapportHajjarDto> data = service.genererRapport(date);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rapport Hajjar");

        // Styles
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(boldFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

        // Couleurs personnalisées par section
        CellStyle sectionTVStyle = colorStyle(workbook, IndexedColors.LIGHT_GREEN);
        CellStyle sectionChauxStyle = colorStyle(workbook, IndexedColors.LIGHT_TURQUOISE);
        CellStyle sectionCCStyle = colorStyle(workbook, IndexedColors.CORAL);

        // Titre rapport (ligne 0)
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("RAPPORT ACTIVITÉS HAJJAR");
        titleCell.setCellStyle(boldCentered(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 6));
        int rowIndex = 2;

        // Regroupement par section
        Map<String, List<RapportHajjarDto>> grouped = data.stream()
                .collect(Collectors.groupingBy(RapportHajjarDto::getSection));

        for (String section : grouped.keySet()) {
            // Section caption
            Row sectionRow = sheet.createRow(rowIndex++);
            Cell sectionCell = sectionRow.createCell(0);
            sectionCell.setCellValue(section);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowIndex - 1, rowIndex - 1, 0, 6));
            sectionCell.setCellStyle(switch (section) {
                case "Arrivages TV Chantier" -> sectionTVStyle;
                case "Arrivages Chaux" -> sectionChauxStyle;
                case "Expéditions CC" -> sectionCCStyle;
                default -> headerStyle;
            });

            // Entête tableau
            Row header = sheet.createRow(rowIndex++);
            String[] titles = {"Désignation", "P1", "P2", "P3", "Jour", "Mois", "Année"};
            for (int i = 0; i < titles.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(headerStyle);
            }

            // Données & totaux
            double totalP1 = 0, totalP2 = 0, totalP3 = 0, totalJ = 0, totalM = 0, totalA = 0;
            for (RapportHajjarDto row : grouped.get(section)) {
                Row r = sheet.createRow(rowIndex++);
                r.createCell(0).setCellValue(row.getDesignation());
                r.createCell(1).setCellValue(row.getP1());        totalP1 += row.getP1();
                r.createCell(2).setCellValue(row.getP2());        totalP2 += row.getP2();
                r.createCell(3).setCellValue(row.getP3());        totalP3 += row.getP3();
                r.createCell(4).setCellValue(row.getTotalJour());totalJ += row.getTotalJour();
                r.createCell(5).setCellValue(row.getTotalMois());totalM += row.getTotalMois();
                r.createCell(6).setCellValue(row.getTotalAnnee());totalA += row.getTotalAnnee();

                for (int i = 1; i <= 6; i++) r.getCell(i).setCellStyle(numberStyle);
            }

            // Ligne total global pour la section
            Row totalRow = sheet.createRow(rowIndex++);
            Cell label = totalRow.createCell(0);
            label.setCellValue("Global");
            label.setCellStyle(headerStyle);

            double[] vals = {totalP1, totalP2, totalP3, totalJ, totalM, totalA};
            for (int i = 0; i < vals.length; i++) {
                Cell cell = totalRow.createCell(i + 1);
                cell.setCellValue(vals[i]);
                cell.setCellStyle(numberStyle);
            }

            rowIndex++; // ligne vide
        }

        for (int i = 0; i <= 6; i++) sheet.autoSizeColumn(i);

        // Config HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String filename = "rapport_hajjar_" + date + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private CellStyle colorStyle(Workbook wb, IndexedColors color) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private CellStyle boldCentered(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        return style;
    }
}
