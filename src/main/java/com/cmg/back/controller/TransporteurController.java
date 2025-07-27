// controller/TransporteurController.java
package com.cmg.back.controller;

import com.cmg.back.model.Transporteur;
import com.cmg.back.repository.TransporteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transporteurs")
public class TransporteurController {

    @Autowired
    private TransporteurRepository repo;

    @GetMapping
    public List<Transporteur> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Transporteur add(@RequestBody Transporteur transporteur) {
        return repo.save(transporteur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
