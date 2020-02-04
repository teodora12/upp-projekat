package com.ftn.upp.service.impl;

import com.ftn.upp.model.Magazine;
import com.ftn.upp.repository.MagazineRepository;
import com.ftn.upp.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    @Autowired
    private MagazineRepository magazineRepository;

    @Override
    public List<Magazine> findAll() {
        return this.magazineRepository.findAll();
    }
}
