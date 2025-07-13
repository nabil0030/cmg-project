package com.cmg.back.service;

import com.cmg.back.model.ChauxViveLafarge;
import com.cmg.back.repository.ChauxViveLafargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChauxViveLafargeService {

    @Autowired
    private ChauxViveLafargeRepository repository;

    public List<ChauxViveLafarge> getAll() {
        return repository.findAll();
    }

    public String add(ChauxViveLafarge entry) {
        entry.setNetCmg(entry.getTb() - entry.getTare());
        entry.setEcart(entry.getNetCmg() - entry.getNetLafarge());

        boolean exists = repository.findByDateAndHeureEntreeAndHeureSortieAndTransporteurAndNumeroBLAndImmatriculeAndTbAndTareAndNetLafargeAndPosteAndLieuChargementAndLieuDechargementAndObservation(
                entry.getDate(), entry.getHeureEntree(), entry.getHeureSortie(), entry.getTransporteur(),
                entry.getNumeroBL(), entry.getImmatricule(), entry.getTb(), entry.getTare(), entry.getNetLafarge(),
                entry.getPoste(), entry.getLieuChargement(), entry.getLieuDechargement(), entry.getObservation()
        ).isPresent();

        if (exists) {
            return "Doublon : cette ligne existe déjà.";
        }

        repository.save(entry);
        return "Ajout réussi";
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public ChauxViveLafarge update(ChauxViveLafarge entry) {
        entry.setNetCmg(entry.getTb() - entry.getTare());
        entry.setEcart(entry.getNetCmg() - entry.getNetLafarge());
        return repository.save(entry);
    }
}
