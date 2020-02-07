package com.ftn.upp.repository;

import com.ftn.upp.model.KeyTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyTermRepository extends JpaRepository<KeyTerm,Long> {

    KeyTerm findKeyTermByName(String name);
    List<KeyTerm> findAll();
}
