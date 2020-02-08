package com.ftn.upp.service.impl;

import com.ftn.upp.model.Dues;
import com.ftn.upp.repository.DuesRepository;
import com.ftn.upp.service.DuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DuesServiceImpl implements DuesService {

    @Autowired
    private DuesRepository duesRepository;

    @Override
    public void saveDues(Dues dues) {
        this.duesRepository.save(dues);
    }

    @Override
    public Dues findDuesById(Long id) {
        return this.duesRepository.findDuesById(id);
    }
}
