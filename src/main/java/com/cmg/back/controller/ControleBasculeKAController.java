package com.cmg.back.controller;

import com.cmg.back.model.ControleBasculeKA;
import com.cmg.back.repository.ControleBasculeKARepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ka")
@CrossOrigin(origins = "*")
public class ControleBasculeKAController {

    @Autowired
    private ControleBasculeKARepository repository;

    @GetMapping
    public List<ControleBasculeKA> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public String create(@RequestBody ControleBasculeKA item) {
        item.setNet(item.getTb() - item.getTare());
        boolean dup = repository.isDuplicate(
                item.getDate(), item.getHEntree(), item.getHSortie(),
                item.getTransporteur(), item.getNumeroBL(), item.getImmatricule(),
                item.getTb(), item.getTare(), item.getPoste(), item.getLieuDeDecharge()
        );
        if (dup) {
            return "⚠️ Doublon détecté : cette entrée existe déjà.";
        }
        repository.save(item);
        return "✅ Enregistrement réussi.";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody ControleBasculeKA item) {
        item.setId(id);
        item.setNet(item.getTb() - item.getTare());
        boolean dup = repository.isDuplicate(
                item.getDate(), item.getHEntree(), item.getHSortie(),
                item.getTransporteur(), item.getNumeroBL(), item.getImmatricule(),
                item.getTb(), item.getTare(), item.getPoste(), item.getLieuDeDecharge()
        );
        if (dup) {
            return "⚠️ Doublon détecté : cette entrée existe déjà.";
        }
        repository.save(item);
        return "✅ Mise à jour réussie.";
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=controle_ka_" + java.time.LocalDate.now() + ".xlsx"
        );
        List<ControleBasculeKA> list = repository.findAll();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("KA");
        String[] titles = {
                "Date","H Entrée","H Sortie","Transporteur","N° BL",
                "Immatricule","TB","TARE","NET","Poste","Lieu Décharge"
        };
        Row h = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            h.createCell(i).setCellValue(titles[i]);
        }
        int r = 1;
        for (ControleBasculeKA c : list) {
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(c.getDate().toString());
            row.createCell(1).setCellValue(c.getHEntree().toString());
            row.createCell(2).setCellValue(c.getHSortie().toString());
            row.createCell(3).setCellValue(c.getTransporteur());
            row.createCell(4).setCellValue(c.getNumeroBL());
            row.createCell(5).setCellValue(c.getImmatricule());
            row.createCell(6).setCellValue(c.getTb());
            row.createCell(7).setCellValue(c.getTare());
            row.createCell(8).setCellValue(c.getNet());
            row.createCell(9).setCellValue(c.getPoste());
            row.createCell(10).setCellValue(c.getLieuDeDecharge());
        }
        for (int i = 0; i < titles.length; i++) sheet.autoSizeColumn(i);
        wb.write(response.getOutputStream());
        wb.close();
    }
}
