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

    @Override
    public boolean isOpenAccess(Magazine magazine) {
        Magazine magazine1 = this.magazineRepository.findMagazineByTitle(magazine.getTitle());
        boolean isOpenAccess = false;
        if(magazine1 != null){
            isOpenAccess = magazine1.isOpenAccess();
        }
        return isOpenAccess;
    }

    @Override
    public void saveMagazine(Magazine magazine) {
        this.magazineRepository.save(magazine);
    }

    @Override
    public Magazine findMagazineByTitle(String title) {
        return this.magazineRepository.findMagazineByTitle(title);
    }
}
