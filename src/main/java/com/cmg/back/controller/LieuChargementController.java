package com.cmg.back.controller;

import com.cmg.back.model.LieuChargement;
import com.cmg.back.repository.LieuChargementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lieux-chargement")
@CrossOrigin(origins = "*")
public class LieuChargementController {

    private final LieuChargementRepository repo;

    public LieuChargementController(LieuChargementRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<LieuChargement> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public LieuChargement add(@RequestBody LieuChargement nouveau) {
        return repo.save(nouveau);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LieuChargement> update(@PathVariable Long id, @RequestBody LieuChargement modif) {
        return repo.findById(id)
                .map(lc -> {
                    lc.setNom(modif.getNom());
                    return ResponseEntity.ok(repo.save(lc));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
