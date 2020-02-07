package com.ftn.upp.repository;

import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByEmail(String email);
    User findUserByUsername(String username);
    User findUserByAuthorities(Authority authority);

}
