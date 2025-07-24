package com.cmg.back.service;

import com.cmg.back.model.ControleBasculeHJDS;
import com.cmg.back.repository.ControleBasculeHJDSRepository;
import com.cmg.back.repository.DsClassiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ControleBasculeHJDSService {

    @Autowired
    private ControleBasculeHJDSRepository repository;

    @Autowired
    private DsClassiqueRepository dsRepo;

    public ControleBasculeHJDS save(ControleBasculeHJDS entity) {
        Double netDs = dsRepo.findNetByNumeroBLAndImmatricule(entity.getNumeroBL(), entity.getImmatricule());

        if (netDs == null) netDs = 0.0;
        entity.setNetDs(netDs);

        if (entity.getNetCmg() != null)
            entity.setEcart(round(netDs - entity.getNetCmg()));
        else
            entity.setEcart(null);

        return repository.save(entity);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
