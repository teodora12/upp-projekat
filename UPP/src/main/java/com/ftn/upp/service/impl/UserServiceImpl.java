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

/*

    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65536;
    private static final int PARALLELISM = 1;


    public String encode(final CharSequence rawPassword) {
        //hash returns already the encoded String
        final String hash = ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, rawPassword.toString());
        return hash;
    }
*/

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

        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setCity(registerDTO.getCity());
        user.setName(registerDTO.getName());
        user.setLastname(registerDTO.getLastname());
        user.setPassword(registerDTO.getPassword());  //ovde dodati encode() kad se ubaci security
        if(!registerDTO.getTitle().equals("") && registerDTO.getTitle()!=null){
            user.setTitle(registerDTO.getTitle());
        }
        user.setReviewer(registerDTO.isReviewer());
        user.setCountry(registerDTO.getState());
        user.setActive(true);  //ovo je za sad true, kad se bude slanje mejla uradilo onda ovde ide false

        this.userRepository.save(user);

        return user;


    }
}
