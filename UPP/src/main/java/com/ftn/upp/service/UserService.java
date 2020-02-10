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
    void SendMailForActivation(User user, String processInstanceId) throws InterruptedException;
    User findUserByAuthority(String authority);
    void sendMailsAuthorRedactor(User user) throws InterruptedException;
    void sendMailWorkDenied(User user);
    void sendPdfInMail(User user);
    void sendMailToRedactor(User user);
    void sendMailToAuthorForCorrection(User user,String comment);

}
