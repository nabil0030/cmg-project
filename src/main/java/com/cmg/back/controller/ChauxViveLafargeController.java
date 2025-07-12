package com.cmg.back.controller;

import com.cmg.back.model.ChauxViveLafarge;
import com.cmg.back.repository.ChauxViveLafargeRepository;
import com.cmg.back.service.ChauxViveLafargeExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ChauxViveLafargeController {

    @Autowired
    private ChauxViveLafargeRepository repository;

    @Autowired
    private ChauxViveLafargeExportService exportService;

    @GetMapping("/chauxViveLafarge")
    public String viewPage() {
        return "chauxViveLafarge";
    }

    @PostMapping("/chauxViveLafarge/add")
    public String add(ChauxViveLafarge item) {
        repository.save(item);
        return "redirect:/chauxViveLafarge";
    }

    @PostMapping("/chauxViveLafarge/update")
    public String update(ChauxViveLafarge item) {
        repository.save(item);
        return "redirect:/chauxViveLafarge";
    }

    @GetMapping("/chauxViveLafarge/delete/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/chauxViveLafarge";
    }

    @GetMapping("/export/chauxViveLafarge/excel")
    public void export(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

    @ResponseBody
    @GetMapping("/api/chauxViveLafarge")
    public List<ChauxViveLafarge> getAll() {
        return repository.findAll();
    }

    @ResponseBody
    @GetMapping("/api/chauxViveLafarge/{id}")
    public ChauxViveLafarge getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
