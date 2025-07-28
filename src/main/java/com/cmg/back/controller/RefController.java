package com.cmg.back.controller;

import com.cmg.back.model.Transporteur;
import com.cmg.back.model.Poste;
import com.cmg.back.model.LieuChargement;
import com.cmg.back.model.LieuDechargement;
import com.cmg.back.repository.TransporteurRepository;
import com.cmg.back.repository.PosteRepository;
import com.cmg.back.repository.LieuChargementRepository;
import com.cmg.back.repository.LieuDechargementRepository;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/refs")
@CrossOrigin(origins = "*")
public class RefController {

    private final TransporteurRepository transporteurRepo;
    private final PosteRepository posteRepo;
    private final LieuChargementRepository lieuChargementRepo;
    private final LieuDechargementRepository lieuDechargementRepo;

    public RefController(
            TransporteurRepository transporteurRepo,
            PosteRepository posteRepo,
            LieuChargementRepository lieuChargementRepo,
            LieuDechargementRepository lieuDechargementRepo
    ) {
        this.transporteurRepo = transporteurRepo;
        this.posteRepo = posteRepo;
        this.lieuChargementRepo = lieuChargementRepo;
        this.lieuDechargementRepo = lieuDechargementRepo;
    }

    @GetMapping
    public Map<String, List<String>> getAllReferences() {
        Map<String, List<String>> map = new HashMap<>();

        map.put("transporteurs", transporteurRepo.findAll().stream().map(Transporteur::getNom).toList());
        map.put("postes", posteRepo.findAll().stream().map(Poste::getNom).toList());
        map.put("lieuxDeChargement", lieuChargementRepo.findAll().stream().map(LieuChargement::getNom).toList());
        map.put("lieuxDeDechargement", lieuDechargementRepo.findAll().stream().map(LieuDechargement::getNom).toList());

        return map;
    }
}
