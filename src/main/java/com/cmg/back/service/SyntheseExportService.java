package com.cmg.back.service;

import com.cmg.back.dto.SyntheseCumulAnnuelGroupesDTO;
import com.cmg.back.dto.SyntheseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SyntheseExportService {

    public void exportSyntheseExcel(
            HttpServletResponse response,
            Map<String, List<Double>> monthlyData,
            SyntheseDto annualDto, // Ajouté pour la ligne CUMUL ANNUEL
            SyntheseCumulAnnuelGroupesDTO groupedAnnualData // Ajouté pour la ligne CUMULS ANNUELS REGROUPÉS
    ) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Synthèse Mensuelle CMG");

        // Styles de cellules pour les en-têtes
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Style pour les en-têtes spécifiques (Entrées, Expéditions, etc.)
        CellStyle entreesHeaderStyle = createColoredHeaderStyle(workbook, IndexedColors.ORANGE.getIndex());
        CellStyle expeditionsHeaderStyle = createColoredHeaderStyle(workbook, IndexedColors.TEAL.getIndex());
        CellStyle cumulativeRowStyle = createColoredHeaderStyle(workbook, IndexedColors.LIGHT_BLUE.getIndex());
        CellStyle groupedAnnualRowStyle = createColoredHeaderStyle(workbook, IndexedColors.LIGHT_YELLOW.getIndex());


        // Création des en-têtes (2 lignes)
        Row headerRow1 = sheet.createRow(0);
        Row headerRow2 = sheet.createRow(1);

        // En-têtes fixes
        createCell(headerRow1, 0, "Mois", headerStyle);
        createCell(headerRow2, 0, "Mois", headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 1, 0, 0)); // Merge "Mois"

        // LES ENTRÉES (5 colonnes de données)
        createCell(headerRow1, 1, "LES ENTRÉES (Tout venant externe & chaux)", entreesHeaderStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 1, 5)); // Merge Entrées sur 5 colonnes

        createCell(headerRow2, 1, "DRAA SFAR", headerStyle);
        createCell(headerRow2, 2, "DRAA NORD", headerStyle);
        createCell(headerRow2, 3, "KOUDIAT AÏCHA", headerStyle);
        createCell(headerRow2, 4, "COSUMAR", headerStyle);
        createCell(headerRow2, 5, "LAFARGE", headerStyle);

        // LES EXPÉDITIONS (8 colonnes de données)
        createCell(headerRow1, 6, "LES EXPÉDITIONS", expeditionsHeaderStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 6, 13)); // Merge Expéditions sur 8 colonnes

        createCell(headerRow2, 6, "CU MRK", headerStyle);
        createCell(headerRow2, 7, "CU NORD", headerStyle);
        createCell(headerRow2, 8, "PB MRK", headerStyle);
        createCell(headerRow2, 9, "C.BULK", headerStyle);
        createCell(headerRow2, 10, "ZN ONCF", headerStyle);
        createCell(headerRow2, 11, "ZN PORT", headerStyle);
        createCell(headerRow2, 12, "ZN VRAC", headerStyle);
        createCell(headerRow2, 13, "ZN Safi", headerStyle);


        // Remplir les données mensuelles
        int rowNum = 2;
        String[] frenchMonthNames = {
                "JANVIER", "FÉVRIER", "MARS", "AVRIL", "MAI", "JUIN",
                "JUILLET", "AOÛT", "SEPTEMBRE", "OCTOBRE", "NOVEMBRE", "DÉCEMBRE"
        };

        for (String monthName : frenchMonthNames) {
            Row row = sheet.createRow(rowNum++);
            List<Double> monthValues = monthlyData.get(monthName.toUpperCase()); // Utiliser toUpperCase pour la clé

            createCell(row, 0, monthName, null); // Mois

            if (monthValues != null && monthValues.size() == 15) {
                // Entrées (indices 0 à 4 de la liste monthlyData)
                for (int i = 0; i <= 4; i++) {
                    createCell(row, i + 1, monthValues.get(i), null);
                }
                // Expéditions (indices 6 à 13 de la liste monthlyData)
                // Expéditions (indices 6 à 13 de la liste monthlyData)
// Les colonnes 6 à 13 du fichier Excel correspondent aux indices 6→13 de votre liste
                for (int i = 6; i <= 13; i++) {
                    createCell(row, i, monthValues.get(i), null);
                }

            } else {
                // Si pas de données, ou données incorrectes, remplir avec 0.0 pour les 13 colonnes de données
                for (int i = 0; i < 13; i++) {
                    createCell(row, i + 1, 0.0, null);
                }
            }
        }

        // --- Ligne CUMUL ANNUEL ---
        Row annualRow = sheet.createRow(rowNum++);
        createCell(annualRow, 0, "CUMUL ANNUEL " + annualDto.getAnnee(), cumulativeRowStyle);

        // Entrées (5 valeurs individuelles de annualDto)
        createCell(annualRow, 1, Objects.requireNonNullElse(annualDto.getDsClassique(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 2, Objects.requireNonNullElse(annualDto.getDsNord(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 3, Objects.requireNonNullElse(annualDto.getControleBasculeKA(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 4, Objects.requireNonNullElse(annualDto.getChauxVive(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 5, Objects.requireNonNullElse(annualDto.getChauxViveLafarge(), 0.0), cumulativeRowStyle);

        // Expéditions (8 valeurs individuelles de annualDto)
        createCell(annualRow, 6, Objects.requireNonNullElse(annualDto.getExpCuivreOncf(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 7, Objects.requireNonNullElse(annualDto.getExpCuivreNord(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 8, Objects.requireNonNullElse(annualDto.getExpPbCmgOnf(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 9, Objects.requireNonNullElse(annualDto.getCbulkArgentifere(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 10, Objects.requireNonNullElse(annualDto.getExpZincOncf(), 0.0), cumulativeRowStyle);
        createCell(annualRow, 11, 0.0, cumulativeRowStyle); // ZN PORT (hardcoded 0.0)
        createCell(annualRow, 12, 0.0, cumulativeRowStyle); // ZN VRAC (hardcoded 0.0)
        createCell(annualRow, 13, Objects.requireNonNullElse(annualDto.getExpZincSafi(), 0.0), cumulativeRowStyle);


        // --- Ligne CUMULS ANNUELS REGROUPÉS ---
        Row groupedAnnualRow = sheet.createRow(rowNum++);
        createCell(groupedAnnualRow, 0, "CUMULS ANNUELS REGROUPÉS " + annualDto.getAnnee(), groupedAnnualRowStyle);
        // Fusionner la cellule du libellé sur 3 colonnes pour qu'elle corresponde à l'image
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(groupedAnnualRow.getRowNum(), groupedAnnualRow.getRowNum(), 0, 2));

        // Calcul des valeurs regroupées (comme dans le frontend JS)
        // Utilisation de groupedAnnualData car c'est le DTO qui contient ces sommes pré-calculées du backend
        double cumulDsNordKa = Objects.requireNonNullElse(groupedAnnualData.getCUMUL_ANNEE_DS_NORD_KA(), 0.0);
        double cumulChaux = Objects.requireNonNullElse(groupedAnnualData.getCUMUL_ANNEE_CHAUX_COSUMAR_LAFARGE(), 0.0);
        double cumulAutres = Objects.requireNonNullElse(groupedAnnualData.getCUMUL_ANNEE_AUTRES(), 0.0);


        // Placer les valeurs dans des cellules distinctes pour correspondre à l'image
        // Les positions sont ajustées pour correspondre à l'image où "DS/NORD/KA" est sous "KOUDIAT AÏCHA"
        // et les autres suivent. Cela implique de laisser des cellules vides avant.
        createCell(groupedAnnualRow, 3, "DS/NORD/KA: " + String.format("%.2f", cumulDsNordKa), groupedAnnualRowStyle);
        createCell(groupedAnnualRow, 4, "CHAUX: " + String.format("%.2f", cumulChaux), groupedAnnualRowStyle);
        createCell(groupedAnnualRow, 5, "AUTRES: " + String.format("%.2f", cumulAutres), groupedAnnualRowStyle);

        // Remplir les cellules restantes avec des chaînes vides pour l'alignement
        // Il y a 14 colonnes au total (0 à 13).
        // Le libellé prend 3 colonnes (0-2). Les 3 valeurs prennent les colonnes 3-5.
        // Il reste 13 - 5 = 8 colonnes à remplir avec des vides.
        for (int i = 6; i <= 13; i++) {
            createCell(groupedAnnualRow, i, "", groupedAnnualRowStyle);
        }

        // Auto-dimensionnement des colonnes
        for (int i = 0; i < 14; i++) { // 14 colonnes au total (Mois + 5 Entrées + 8 Expéditions)
            sheet.autoSizeColumn(i);
        }

        // Configuration de la réponse HTTP pour le téléchargement
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=synthese_cmg.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createCell(Row row, int column, Object value, CellStyle style) {
        Cell cell = row.createCell(column);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private CellStyle createColoredHeaderStyle(Workbook workbook, short colorIndex) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
}
