package com.cmg.back.controller;

import com.cmg.back.model.ExpZincOncf;
import com.cmg.back.repository.ExpZincOncfRepository;
import com.cmg.back.service.ExpZincOncfExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/expzinconcf")
@CrossOrigin(origins = "*")
public class ExpZincOncfController {

    @Autowired
    private ExpZincOncfRepository repository;

    @GetMapping
    public List<ExpZincOncf> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ExpZincOncf getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non trouvé"));
    }

    @PostMapping
    public ExpZincOncf create(@RequestBody ExpZincOncf record) {
        record.setTnH(record.getTb() - record.getTare());

        if (existsDuplicate(record)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Doublon détecté");
        }

        return repository.save(record);
    }

    @PutMapping("/{id}")
    public ExpZincOncf update(@PathVariable Long id, @RequestBody ExpZincOncf record) {
        record.setId(id);
        record.setTnH(record.getTb() - record.getTare());

        if (existsDuplicate(record)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Doublon détecté");
        }

        return repository.save(record);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private boolean existsDuplicate(ExpZincOncf record) {
        return repository.findAll().stream().anyMatch(existing ->
                existing.getDate().equals(record.getDate()) &&
                        existing.getHeureEntree().equals(record.getHeureEntree()) &&
                        existing.getHeureSortie().equals(record.getHeureSortie()) &&
                        existing.getTransporteur().equals(record.getTransporteur()) &&
                        existing.getNumeroBL().equals(record.getNumeroBL()) &&
                        existing.getMatricule().equals(record.getMatricule()) &&
                        Double.compare(existing.getTb(), record.getTb()) == 0 &&
                        Double.compare(existing.getTare(), record.getTare()) == 0 &&
                        existing.getNumeroContenaire().equals(record.getNumeroContenaire()) &&
                        existing.getPoste() == record.getPoste() &&
                        existing.getLieuDeDechargement().equals(record.getLieuDeDechargement()) &&
                        (record.getId() == null || !existing.getId().equals(record.getId()))
        );
    }
    @Autowired
    private ExpZincOncfExportService exportService;

    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

}
