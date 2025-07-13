package com.cmg.back.service;

import com.cmg.back.model.ControleBasculeKA;
import com.cmg.back.repository.ControleBasculeKARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ControleBasculeKAService {

    private final ControleBasculeKARepository repo;

    public ControleBasculeKAService(ControleBasculeKARepository repo) {
        this.repo = repo;
    }

    public List<ControleBasculeKA> getAll() {
        return repo.findAll();
    }

    public Optional<ControleBasculeKA> getById(Long id) {
        return repo.findById(id);
    }

    public boolean isDuplicate(ControleBasculeKA c) {
        return repo.isDuplicate(
                c.getDate(), c.getHEntree(), c.getHSortie(),
                c.getTransporteur(), c.getNumeroBL(), c.getImmatricule(),
                c.getTb(), c.getTare(), c.getPoste(), c.getLieuDeDecharge()
        );
    }

    public ControleBasculeKA save(ControleBasculeKA c) {
        return repo.save(c);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
