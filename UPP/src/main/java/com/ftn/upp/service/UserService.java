package com.ftn.upp.service;

import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User register(RegisterDTO registerDTO);

}
