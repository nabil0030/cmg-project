package com.cmg.back.service;

import com.cmg.back.model.ChauxVive;
import com.cmg.back.repository.ChauxViveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChauxViveService {
    @Autowired
    private ChauxViveRepository repository;

    public List<ChauxVive> findAll() { return repository.findAll(); }

    public Optional<ChauxVive> findById(Long id) { return repository.findById(id); }

    public ChauxVive save(ChauxVive entry) {
        if (repository.existsDuplicate(
                entry.getDate(), entry.getHEntree(), entry.getHSortie(),
                entry.getTransporteur(), entry.getNumeroBL(), entry.getImmat(),
                entry.getTb(), entry.getTare(), entry.getPoste(),
                entry.getNetCosumar(), entry.getLieuChargement(),
                entry.getLieuDechargement(), entry.getObservation())
        ) {
            throw new RuntimeException("Doublon détecté !");
        }
        return repository.save(entry);
    }

    public void delete(Long id) { repository.deleteById(id); }

    public ChauxVive update(Long id, ChauxVive entry) {
        if (!repository.existsById(id)) throw new RuntimeException("Non trouvé");
        if (repository.existsDuplicate(
                entry.getDate(), entry.getHEntree(), entry.getHSortie(),
                entry.getTransporteur(), entry.getNumeroBL(), entry.getImmat(),
                entry.getTb(), entry.getTare(), entry.getPoste(),
                entry.getNetCosumar(), entry.getLieuChargement(),
                entry.getLieuDechargement(), entry.getObservation())
        ) {
            throw new RuntimeException("Doublon détecté !");
        }
        entry.setId(id);
        return repository.save(entry);
    }
}
