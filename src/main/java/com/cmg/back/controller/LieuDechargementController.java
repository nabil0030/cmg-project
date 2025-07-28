package com.cmg.back.controller;

import com.cmg.back.model.LieuDechargement;
import com.cmg.back.repository.LieuDechargementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lieux-dechargement")
@CrossOrigin(origins = "*")
public class LieuDechargementController {

    private final LieuDechargementRepository repo;

    public LieuDechargementController(LieuDechargementRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<LieuDechargement> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public LieuDechargement add(@RequestBody LieuDechargement nouveau) {
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
    public ResponseEntity<LieuDechargement> update(@PathVariable Long id, @RequestBody LieuDechargement modif) {
        return repo.findById(id)
                .map(ld -> {
                    ld.setNom(modif.getNom());
                    return ResponseEntity.ok(repo.save(ld));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
