package com.cmg.back.controller;

import com.cmg.back.model.Transporteur;
import com.cmg.back.repository.TransporteurRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transporteurs")
@CrossOrigin("*")
public class TransporteurController {
    private final TransporteurRepository repo;
    public TransporteurController(TransporteurRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Transporteur> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Transporteur add(@RequestBody Transporteur t) {
        return repo.save(t);
    }

    @PutMapping("/{id}")
    public Transporteur update(@PathVariable Long id, @RequestBody Transporteur updated) {
        Transporteur t = repo.findById(id).orElse(null);
        if (t != null) {
            t.setNom(updated.getNom());
            return repo.save(t);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
