package com.cmg.back.controller;

import com.cmg.back.model.CBulkArgentifere;
import com.cmg.back.repository.CBulkArgentifereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/cbulkargentifere")
@CrossOrigin(origins = "*")
public class CBulkArgentifereController {

    @Autowired
    private CBulkArgentifereRepository repository;

    @GetMapping
    public List<CBulkArgentifere> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public CBulkArgentifere create(@RequestBody CBulkArgentifere record) {
        record.setTnH(record.getTb() - record.getTare());

        if (existsDuplicate(record)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Doublon détecté");
        }

        return repository.save(record);
    }

    @PutMapping("/{id}")
    public CBulkArgentifere update(@PathVariable Long id, @RequestBody CBulkArgentifere record) {
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

    private boolean existsDuplicate(CBulkArgentifere record) {
        return repository.findAll().stream().anyMatch(existing ->
                existing.getDate().equals(record.getDate()) &&
                        existing.gethEntree().equals(record.gethEntree()) &&
                        existing.gethSortie().equals(record.gethSortie()) &&
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
}
