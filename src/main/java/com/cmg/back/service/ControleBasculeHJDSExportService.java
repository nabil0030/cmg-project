package com.cmg.back.export;

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

    private final ControleBasculeHJDSRepository repo;
    public ControleBasculeHJDSExportService(ControleBasculeHJDSRepository r){ this.repo = r; }

    public void exportToExcel(HttpServletResponse resp) throws IOException {
        List<ControleBasculeHJDS> list = repo.findAll();

        Workbook wb = new XSSFWorkbook();
        Sheet    sh = wb.createSheet("Bascule HJ‑DS");

        /* en‑tête ---------------------------------------------------- */
        Row head = sh.createRow(0);
        String[] cols = {"Date","Transporteur","N° BL","Immatricule",
                "Net DS","Net CMG","Écart","Observation"};
        for(int i=0;i<cols.length;i++) head.createCell(i).setCellValue(cols[i]);

        /* lignes ----------------------------------------------------- */
        int row = 1;
        for (ControleBasculeHJDS e : list) {
            Row r = sh.createRow(row++);
            r.createCell(0).setCellValue(e.getDate().toString());
            r.createCell(1).setCellValue(e.getTransporteur());
            r.createCell(2).setCellValue(e.getNumeroBL());
            r.createCell(3).setCellValue(e.getImmatricule());
            r.createCell(4).setCellValue(e.getNetDs());
            r.createCell(5).setCellValue(e.getNetCmg());
            r.createCell(6).setCellValue(e.getEcart());
            r.createCell(7).setCellValue(e.getObservation());
        }

        /* sortie ----------------------------------------------------- */
        resp.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition",
                "attachment; filename=controle_bascule_hjds.xlsx");

        try (ServletOutputStream out = resp.getOutputStream()) {
            wb.write(out);
        }
        wb.close();
    }
}
