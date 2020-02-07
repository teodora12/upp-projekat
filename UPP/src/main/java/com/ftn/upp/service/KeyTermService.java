package com.ftn.upp.service;

import com.ftn.upp.model.KeyTerm;
import org.springframework.stereotype.Service;

@Service
public interface KeyTermService {

    KeyTerm findKeyTermByName(String name);
    void saveKeyTerm(KeyTerm keyTerm);
}
