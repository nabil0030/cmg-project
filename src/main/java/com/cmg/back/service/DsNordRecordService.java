package com.cmg.back.service;

import com.cmg.back.model.DsNordRecord;
import com.cmg.back.repository.DsNordRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DsNordRecordService {

    private final DsNordRecordRepository repository;

    public DsNordRecordService(DsNordRecordRepository repository) {
        this.repository = repository;
    }

    public List<DsNordRecord> getAll() {
        return repository.findAll();
    }

    public Optional<DsNordRecord> getById(Long id) {
        return repository.findById(id);
    }

    public boolean isDuplicate(DsNordRecord r) {
        // on suppose que r.getNet() est calculé en @PrePersist/@PreUpdate dans le modèle
        return repository.isDuplicate(
                r.getDate(), r.getHEntree(), r.getHSortie(),
                r.getTransporteur(), r.getNumeroBL(), r.getImmatricule(),
                r.getTb(), r.getTare(), r.getPoste(),
                r.getLieuDeDecharge(), r.getObservation()
        );
    }

    public DsNordRecord save(DsNordRecord r) {
        return repository.save(r);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
