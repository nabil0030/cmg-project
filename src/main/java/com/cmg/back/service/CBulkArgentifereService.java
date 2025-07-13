package com.cmg.back.service;

import com.cmg.back.model.CBulkArgentifere;
import com.cmg.back.repository.CBulkArgentifereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CBulkArgentifereService {

    @Autowired
    private CBulkArgentifereRepository repository;

    public List<CBulkArgentifere> getAll() {
        return repository.findAll();
    }

    public String add(CBulkArgentifere entry) {
        entry.setTnh(entry.getTb() - entry.getTare());

        boolean exists = repository.findByDateAndHeureEntreeAndHeureSortieAndTransporteurAndNumeroBLAndMatriculeAndTbAndTareAndNumeroContenaireAndPosteAndLieuDechargementAndObservation(
                entry.getDate(), entry.getHeureEntree(), entry.getHeureSortie(), entry.getTransporteur(),
                entry.getNumeroBL(), entry.getMatricule(), entry.getTb(), entry.getTare(),
                entry.getNumeroContenaire(), entry.getPoste(), entry.getLieuDechargement(), entry.getObservation()
        ).isPresent();

        if (exists) return "Doublon détecté : ligne déjà existante.";
        repository.save(entry);
        return "Ajout réussi";
    }

    public CBulkArgentifere update(CBulkArgentifere entry) {
        entry.setTnh(entry.getTb() - entry.getTare());
        return repository.save(entry);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
