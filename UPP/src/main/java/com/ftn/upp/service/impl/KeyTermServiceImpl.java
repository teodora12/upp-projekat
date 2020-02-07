package com.ftn.upp.service.impl;


import com.ftn.upp.model.KeyTerm;
import com.ftn.upp.repository.KeyTermRepository;
import com.ftn.upp.service.KeyTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyTermServiceImpl implements KeyTermService {

    @Autowired
    private KeyTermRepository keyTermRepository;

    @Override
    public KeyTerm findKeyTermByName(String name) {
        return this.keyTermRepository.findKeyTermByName(name);
    }

    @Override
    public void saveKeyTerm(KeyTerm keyTerm) {
        this.keyTermRepository.save(keyTerm);
    }
}
