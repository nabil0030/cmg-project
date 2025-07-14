package com.cmg.back.service;

import com.cmg.back.model.ExpZincSafi;
import com.cmg.back.repository.ExpZincSafiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpZincSafiService {
    @Autowired
    private ExpZincSafiRepository repository;

    public List<ExpZincSafi> findAll() { return repository.findAll(); }

    public Optional<ExpZincSafi> findById(Long id) { return repository.findById(id); }

    public ExpZincSafi save(ExpZincSafi entry) {
        if (repository.existsDuplicate(
                entry.getDate(), entry.getHEntree(), entry.getHSortie(),
                entry.getTransporteur(), entry.getNumeroBL(), entry.getMatricule(),
                entry.getTb(), entry.getTare(), entry.getPoste(), entry.getLieuDeDechargement(),
                entry.getObservation())
        ) {
            throw new RuntimeException("Doublon détecté !");
        }
        return repository.save(entry);
    }

    public void delete(Long id) { repository.deleteById(id); }

    public ExpZincSafi update(Long id, ExpZincSafi entry) {
        if (!repository.existsById(id)) throw new RuntimeException("Non trouvé");
        if (repository.existsDuplicate(
                entry.getDate(), entry.getHEntree(), entry.getHSortie(),
                entry.getTransporteur(), entry.getNumeroBL(), entry.getMatricule(),
                entry.getTb(), entry.getTare(), entry.getPoste(), entry.getLieuDeDechargement(),
                entry.getObservation())
        ) {
            throw new RuntimeException("Doublon détecté !");
        }
        entry.setId(id);
        return repository.save(entry);
    }
}
