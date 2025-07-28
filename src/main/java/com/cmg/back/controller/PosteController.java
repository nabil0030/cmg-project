package com.cmg.back.controller;

import com.cmg.back.model.Poste;
import com.cmg.back.repository.PosteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postes")
@CrossOrigin("*")
public class PosteController {
    private final PosteRepository repo;
    public PosteController(PosteRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Poste> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Poste add(@RequestBody Poste p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Poste update(@PathVariable Long id, @RequestBody Poste updated) {
        Poste p = repo.findById(id).orElse(null);
        if (p != null) {
            p.setNom(updated.getNom());
            return repo.save(p);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
