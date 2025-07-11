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

    @GetMapping("/{id}")
    public ControleBasculeKA getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public ControleBasculeKA create(@RequestBody ControleBasculeKA item) {
        return repository.save(item);
    }

    @PutMapping("/{id}")
    public ControleBasculeKA update(@PathVariable Long id, @RequestBody ControleBasculeKA updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=koudiat-aicha.xlsx");

        List<ControleBasculeKA> list = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("KA");

        Row header = sheet.createRow(0);
        String[] titles = {"DATE", "H Entrée", "H Sortie", "Transporteur", "N° BL", "Immatricule", "TB", "TARE", "NET", "Poste", "Lieu Décharge"};
        for (int i = 0; i < titles.length; i++) {
            header.createCell(i).setCellValue(titles[i]);
        }

        int rowIdx = 1;
        for (ControleBasculeKA r : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(r.getDate());
            row.createCell(1).setCellValue(r.getHEntree());
            row.createCell(2).setCellValue(r.getHSortie());
            row.createCell(3).setCellValue(r.getTransporteur());
            row.createCell(4).setCellValue(r.getNumeroBL());
            row.createCell(5).setCellValue(r.getImmatricule());
            row.createCell(6).setCellValue(r.getTb());
            row.createCell(7).setCellValue(r.getTare());
            row.createCell(8).setCellValue(r.getNet());
            row.createCell(9).setCellValue(r.getPoste());
            row.createCell(10).setCellValue(r.getLieuDeDecharge());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
