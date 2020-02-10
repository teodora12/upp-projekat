package com.ftn.upp.repository;

import com.ftn.upp.model.User;
import com.ftn.upp.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work,Long> {

    Work findWorkById(Long id);
    Work findWorkByTitle(String title);
    List<Work> findAll();
    List<Work> findWorksByUsers(User user);
    Work findWorkByUsers(User user);
}
