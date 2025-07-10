package com.cmg.back.controller;

import com.cmg.back.model.ControleBasculeHJDS;
import com.cmg.back.repository.ControleBasculeHJDSRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bascule-hj-ds")
@CrossOrigin(origins = "*") // autorise les requêtes depuis le HTML
public class ControleBasculeHJDSController {

    private final ControleBasculeHJDSRepository repository;

    public ControleBasculeHJDSController(ControleBasculeHJDSRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ControleBasculeHJDS> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public ControleBasculeHJDS create(@RequestBody ControleBasculeHJDS record) {
        if (record.getNetDS() != null && record.getNetCMG() != null) {
            record.setEcart(Math.abs(record.getNetDS() - record.getNetCMG()));
        }
        return repository.save(record);
    }
}
