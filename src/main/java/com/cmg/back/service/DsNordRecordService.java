package com.cmg.back.service;

import com.cmg.back.model.DsNordRecord;
import com.cmg.back.repository.DsNordRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DsNordRecordService {

    @Autowired
    private DsNordRecordRepository repository;

    public List<DsNordRecord> getAll() {
        return repository.findAll();
    }

    public Optional<DsNordRecord> getById(Long id) {
        return repository.findById(id);
    }

    public DsNordRecord save(DsNordRecord record) {
        return repository.save(record);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
