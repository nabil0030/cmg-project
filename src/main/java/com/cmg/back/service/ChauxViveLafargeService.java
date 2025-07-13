package com.cmg.back.service;

import com.cmg.back.model.ChauxViveLafarge;
import com.cmg.back.repository.ChauxViveLafargeRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ChauxViveLafargeService {

    @Autowired
    private ChauxViveLafargeRepository repository;

    public List<ChauxViveLafarge> getAll() {
        return repository.findAll();
    }

    public String add(ChauxViveLafarge entry) {
        entry.setNetCmg(entry.getTb() - entry.getTare());
        entry.setEcart(entry.getNetCmg() - entry.getNetLafarge());

        boolean exists = repository.findByDateAndHeureEntreeAndHeureSortieAndTransporteurAndNumeroBLAndImmatriculeAndTbAndTareAndNetLafargeAndPosteAndLieuChargementAndLieuDechargementAndObservation(
                entry.getDate(), entry.getHeureEntree(), entry.getHeureSortie(), entry.getTransporteur(),
                entry.getNumeroBL(), entry.getImmatricule(), entry.getTb(), entry.getTare(), entry.getNetLafarge(),
                entry.getPoste(), entry.getLieuChargement(), entry.getLieuDechargement(), entry.getObservation()
        ).isPresent();

        if (exists) {
            return "Doublon : cette ligne existe déjà.";
        }

        repository.save(entry);
        return "Ajout réussi";
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ChauxViveLafarge update(ChauxViveLafarge entry) {
        entry.setNetCmg(entry.getTb() - entry.getTare());
        entry.setEcart(entry.getNetCmg() - entry.getNetLafarge());
        return repository.save(entry);
    }

    // ✅ Export Excel
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ChauxViveLafarge> list = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chaux Vive Lafarge");

        // En-têtes
        Row header = sheet.createRow(0);
        String[] columns = {
                "Date", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Immatricule",
                "TB", "TARE", "NET CMG", "NET LAFARGE", "ÉCART",
                "Poste", "Lieu Chargement", "Lieu Déchargement", "Observation"
        };
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        // Données
        int rowNum = 1;
        for (ChauxViveLafarge cv : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cv.getDate());
            row.createCell(1).setCellValue(cv.getHeureEntree().toString());
            row.createCell(2).setCellValue(cv.getHeureSortie().toString());
            row.createCell(3).setCellValue(cv.getTransporteur());
            row.createCell(4).setCellValue(cv.getNumeroBL());
            row.createCell(5).setCellValue(cv.getImmatricule());
            row.createCell(6).setCellValue(cv.getTb());
            row.createCell(7).setCellValue(cv.getTare());
            row.createCell(8).setCellValue(cv.getNetCmg());
            row.createCell(9).setCellValue(cv.getNetLafarge());
            row.createCell(10).setCellValue(cv.getEcart());
            row.createCell(11).setCellValue(cv.getPoste());
            row.createCell(12).setCellValue(cv.getLieuChargement());
            row.createCell(13).setCellValue(cv.getLieuDechargement());
            row.createCell(14).setCellValue(cv.getObservation());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Réponse HTTP avec nom dynamique
        String filename = "chaux_vive_lafarge_" + LocalDate.now() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();
    }
}
