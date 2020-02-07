package com.ftn.upp.service.impl;

import com.ftn.upp.model.Work;
import com.ftn.upp.repository.WorkRepository;
import com.ftn.upp.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkRepository workRepository;

    @Override
    public Work findWorkByTitle(String title) {
        return this.workRepository.findWorkByTitle(title);
    }

    @Override
    public Work findWorkById(Long id) {
        return this.workRepository.findWorkById(id);
    }

    @Override
    public void saveWork(Work work) {
        this.workRepository.save(work);
    }
}
