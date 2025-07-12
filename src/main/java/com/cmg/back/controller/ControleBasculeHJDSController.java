package com.cmg.back.controller;

import com.cmg.back.model.ControleBasculeHJDS;
import com.cmg.back.repository.ControleBasculeHJDSRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bascule-hj-ds")
@CrossOrigin(origins = "*")
public class ControleBasculeHJDSController {

    private final ControleBasculeHJDSRepository repository;

    public ControleBasculeHJDSController(ControleBasculeHJDSRepository repository) {
        this.repository = repository;
    }

    // GET all - triés par date DESC
    @GetMapping
    public List<ControleBasculeHJDS> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ControleBasculeHJDS> getById(@PathVariable Long id) {
        Optional<ControleBasculeHJDS> record = repository.findById(id);
        return record.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST (ajout) avec vérification duplicata
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ControleBasculeHJDS record) {
        if (repository.existsByDateAndTransporteurAndNumeroBLAndImmatriculeAndNetDSAndNetCMG(
                record.getDate(), record.getTransporteur(), record.getNumeroBL(),
                record.getImmatricule(), record.getNetDS(), record.getNetCMG())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicata détecté");
        }

        record.setEcart(record.getNetCMG() - record.getNetDS());
        repository.save(record);
        return ResponseEntity.ok("Ajouté");
    }

    // PUT (modifier)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ControleBasculeHJDS updated) {
        return repository.findById(id).map(record -> {
            record.setDate(updated.getDate());
            record.setTransporteur(updated.getTransporteur());
            record.setNumeroBL(updated.getNumeroBL());
            record.setImmatricule(updated.getImmatricule());
            record.setNetDS(updated.getNetDS());
            record.setNetCMG(updated.getNetCMG());
            record.setObservation(updated.getObservation());
            record.setEcart(updated.getNetCMG() - updated.getNetDS());
            repository.save(record);
            return ResponseEntity.ok("Modifié");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Supprimé");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
