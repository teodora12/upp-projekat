package com.ftn.upp.repository;

import com.ftn.upp.model.Dues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuesRepository extends JpaRepository<Dues,Long> {

    Dues findDuesById(Long id);

}
