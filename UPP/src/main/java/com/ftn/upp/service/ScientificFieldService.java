package com.ftn.upp.service;

import com.ftn.upp.model.ScientificField;
import org.springframework.stereotype.Service;

@Service
public interface ScientificFieldService {

    ScientificField findScientificFieldByName(String name);
    void save(ScientificField scientificField);
}
