package com.cmg.back.service;

import com.cmg.back.model.ExpCuivreNord;
import com.cmg.back.repository.ExpCuivreNordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpCuivreNordService {
    @Autowired
    private ExpCuivreNordRepository repository;

    public List<ExpCuivreNord> findAll() { return repository.findAll(); }

    public Optional<ExpCuivreNord> findById(Long id) { return repository.findById(id); }

    public ExpCuivreNord save(ExpCuivreNord entry) {
        if (repository.existsDuplicate(
                entry.getDate(), entry.getHEntree(), entry.getHSortie(),
                entry.getTransporteur(), entry.getNumeroBL(), entry.getMatricule(),
                entry.getTb(), entry.getTare(), entry.getNumeroContenaire(),
                entry.getPoste(), entry.getLieuDeDechargement(), entry.getObservation())
        ) {
            throw new RuntimeException("Doublon détecté !");
        }
        return repository.save(entry);
    }

    public void delete(Long id) { repository.deleteById(id); }

    public ExpCuivreNord update(Long id, ExpCuivreNord entry) {
        if (!repository.existsById(id)) throw new RuntimeException("Non trouvé");
        if (repository.existsDuplicate(
                entry.getDate(), entry.getHEntree(), entry.getHSortie(),
                entry.getTransporteur(), entry.getNumeroBL(), entry.getMatricule(),
                entry.getTb(), entry.getTare(), entry.getNumeroContenaire(),
                entry.getPoste(), entry.getLieuDeDechargement(), entry.getObservation())
        ) {
            throw new RuntimeException("Doublon détecté !");
        }
        entry.setId(id);
        return repository.save(entry);
    }
}
