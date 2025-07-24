package com.cmg.back.controller;

import com.cmg.back.dto.RapportHajjarDto;
import com.cmg.back.service.RapportHajjarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rapport-hajjar")
public class RapportHajjarController {

    @Autowired
    private RapportHajjarService service;

    @GetMapping
    public List<RapportHajjarDto> getRapport(@RequestParam LocalDate date) {
        return service.genererRapport(date);
    }

}