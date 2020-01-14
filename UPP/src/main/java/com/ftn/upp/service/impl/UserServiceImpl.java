package com.ftn.upp.service.impl;

import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(RegisterDTO registerDTO) {

        boolean exsist = false;
        List<User> allUsers = this.userRepository.findAll();
        for(User u : allUsers){
            if(u.getEmail().equals(registerDTO.getEmail())){
                exsist = true;
            }
        }
        if(exsist == true){
            return null;
        }

        return null;
    }
}
