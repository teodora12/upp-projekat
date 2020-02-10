package com.ftn.upp.repository;

import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine,Long> {

    Magazine findMagazineByTitle(String title);
    List<Magazine> findAll();


}
