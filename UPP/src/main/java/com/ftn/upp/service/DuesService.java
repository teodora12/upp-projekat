package com.ftn.upp.service;

import com.ftn.upp.model.Dues;
import org.springframework.stereotype.Service;

@Service
public interface DuesService {

    void saveDues(Dues dues);
    Dues findDuesById(Long id);

}
