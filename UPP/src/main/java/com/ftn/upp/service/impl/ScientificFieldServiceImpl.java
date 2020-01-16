package com.ftn.upp.service.impl;

import com.ftn.upp.model.ScientificField;
import com.ftn.upp.repository.ScientificFieldRepository;
import com.ftn.upp.service.ScientificFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {

    @Autowired
    private ScientificFieldRepository scientificFieldRepository;

    @Override
    public ScientificField findScientificFieldByName(String name) {
        return this.scientificFieldRepository.findScientificFieldByName(name);
    }

    @Override
    public void save(ScientificField scientificField) {
        this.scientificFieldRepository.save(scientificField);
    }
}
