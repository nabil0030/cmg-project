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
    @ResponseBody
    public String add(@ModelAttribute ChauxViveLafarge item) {
        boolean duplicate = repository.existsDuplicate(
                item.getDate(),
                item.getHEntree(),
                item.getHSortie(),
                item.getTransporteur(),
                item.getNumeroBL(),
                item.getImmatricule(),
                item.getTb(),
                item.getTare(),
                item.getPoste(),
                item.getLieuChargement(),
                item.getLieuDechargement()
        );

        if (!duplicate) {
            repository.save(item);
            return "OK";
        } else {
            return "DUPLICATE";
        }
    }

    @PostMapping("/chauxViveLafarge/update")
    @ResponseBody
    public String update(@ModelAttribute ChauxViveLafarge item) {
        repository.save(item);
        return "OK";
    }

    @GetMapping("/chauxViveLafarge/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/export/chauxViveLafarge/excel")
    public void export(HttpServletResponse response) throws IOException {
        exportService.exportToExcel(response);
    }

    @ResponseBody
    @GetMapping("/api/chauxViveLafarge")
    public List<ChauxViveLafarge> getAll() {
        return repository.findAllByOrderByDateAsc();
    }

    @ResponseBody
    @GetMapping("/api/chauxViveLafarge/{id}")
    public ChauxViveLafarge getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
