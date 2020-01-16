package com.ftn.upp.service.impl;

import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

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
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());  //ovde dodati encode() kad se ubaci security
        if(!registerDTO.getTitle().equals("") && registerDTO.getTitle()!=null){
            user.setTitle(registerDTO.getTitle());
        }
        user.setReviewer(registerDTO.isReviewer());
        user.setCountry(registerDTO.getState());
      //  user.(true);  //ovo je za sad true, kad se bude slanje mejla uradilo onda ovde ide false

        this.userRepository.save(user);

        return user;


    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void SendMailForActivation(User user) throws MailException, InterruptedException {

        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Primer slanja emaila pomocu asinhronog Spring taska");
        mail.setText("Pozdrav " + user.getName() + ",\n\nhvala što pratiš ISA.");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }
}
