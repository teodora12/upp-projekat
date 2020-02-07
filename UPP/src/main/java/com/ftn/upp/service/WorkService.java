package com.ftn.upp.service;

import com.ftn.upp.model.Work;
import org.springframework.stereotype.Service;

@Service
public interface WorkService {

    Work findWorkByTitle(String title);
    Work findWorkById(Long id);
    void saveWork(Work work);

}
