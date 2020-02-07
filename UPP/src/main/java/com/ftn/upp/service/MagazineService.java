package com.ftn.upp.service;

import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MagazineService {

    List<Magazine> findAll();
    boolean isOpenAccess(Magazine magazine);
    Magazine findMagazineByTitle(String title);
    void saveMagazine(Magazine magazine);


}
