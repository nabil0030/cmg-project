package com.cmg.back.service;

import com.cmg.back.model.ControleBasculeKA;
import com.cmg.back.repository.ControleBasculeKARepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ControleBasculeKAExportService {

    private final ControleBasculeKARepository repo;

    public ControleBasculeKAExportService(ControleBasculeKARepository repo) {
        this.repo = repo;
    }

    public void exportToExcel(HttpServletResponse res) throws IOException {
        List<ControleBasculeKA> list = repo.findAll();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("C. Bascule KA");

        String[] cols = {
                "Date","H Entrée","H Sortie","Transporteur","N° BL",
                "Immatricule","TB","TARE","NET","Poste","Lieu De Décharge"
        };
        Row hdr = sheet.createRow(0);
        for(int i=0;i<cols.length;i++){
            hdr.createCell(i).setCellValue(cols[i]);
        }

        int r=1;
        for(ControleBasculeKA c: list){
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(c.getDate()!=null?c.getDate().toString():"");
            row.createCell(1).setCellValue(c.getHEntree()!=null?c.getHEntree().toString():"");
            row.createCell(2).setCellValue(c.getHSortie()!=null?c.getHSortie().toString():"");
            row.createCell(3).setCellValue(c.getTransporteur());
            row.createCell(4).setCellValue(c.getNumeroBL());
            row.createCell(5).setCellValue(c.getImmatricule());
            row.createCell(6).setCellValue(c.getTb()!=null?c.getTb():0);
            row.createCell(7).setCellValue(c.getTare()!=null?c.getTare():0);
            row.createCell(8).setCellValue(c.getNet()!=null?c.getNet():0);
            row.createCell(9).setCellValue(c.getPoste());
            row.createCell(10).setCellValue(c.getLieuDeDecharge());
        }
        for(int i=0;i<cols.length;i++) sheet.autoSizeColumn(i);

        String fn = "controle_ka_" + LocalDate.now()+".xlsx";
        res.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
        res.setHeader("Content-Disposition","attachment; filename="+fn);
        ServletOutputStream out = res.getOutputStream();
        wb.write(out);
        wb.close();
        out.close();
    }
}
