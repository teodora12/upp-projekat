package com.ftn.upp.service;

import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User register(RegisterDTO registerDTO);
    User findUserByEmail(String email);
    void saveUser(User user);
    User findUserByUsername(String username);
    List<User> findAll();

}
