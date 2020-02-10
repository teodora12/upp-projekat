package com.ftn.upp.service.impl;

import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.AuthorityService;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
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

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private AuthorityService authorityService;
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
    public User findUserByAuthority(String authority) {
        Authority authority1 = this.authorityService.findAuthorityByName(authority);
        User user = this.userRepository.findUserByAuthorities(authority1);
        return user;
    }

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
    public void SendMailForActivation(User user, String processInstanceId) throws MailException, InterruptedException {

        System.out.println("Slanje emaila za aktivaciju naloga...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Email za aktivaciju naloga");
        String url = "http://localhost:4200/activate/" + user.getEmail() + "/" + processInstanceId;
        mail.setText("Pozdrav " + user.getName()+ "! Vas nalog bice aktiviran klikom na sledeci link i potvrdom aktivacije naloga: " + url + ".");

   //     javaMailSender.send(mail);

        System.out.println("Email za aktivaciju naloga poslat!");
    }

    @Override
    public void sendMailWorkDenied(User user) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));

        mail.setSubject("Obavestenje o odbijanju rada");
        mail.setText("Pozdrav " + user.getName() + "! Obavestavamo Vas da je Vas rad odbijen.");

        javaMailSender.send(mail);

        System.out.println("Email da je odbijen rad autoru poslat!");


    }

    @Override
    public void sendPdfInMail(User user) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));

        mail.setSubject("PDF prihvacenog rada u prilogu");

        mail.setText("Pozdrav " + user.getName() + "! U prilogu se nalazi PDF format prihvacenog rada.");

        javaMailSender.send(mail);

        System.out.println("PDF glavnom uredniku poslat!");

    }

    @Override
    public void sendMailToRedactor(User user) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));

        mail.setSubject("Obavestenje o prijavi novog rada");

        mail.setText("Pozdrav " + user.getName() + "! Obavestavamo Vas da je prijavljen novi rad za recenziranje, treba da izaberete recenzente.");

        javaMailSender.send(mail);

        System.out.println("PDF uredniku poslat!");
    }

    @Override
    public void sendMailToAuthorForCorrection(User user, String comment) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));

        mail.setSubject("PDF Vaseg rada u prilogu");

        mail.setText("Pozdrav " + user.getName() + "! U prilogu se nalazi PDF format Vaseg rada koji treba da se ispravi uz obrazlozenje: " + comment);

        javaMailSender.send(mail);

        System.out.println("PDF za ispravku uredniku poslat!");
    }

    @Override
    public void sendMailsAuthorRedactor(User user) throws InterruptedException {

        System.out.println("Slanje emaila glavnom uredniku i autoru zbog prijave rada...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));

        for(GrantedAuthority authority: user.getAuthorities()) {
            if (authority.getAuthority().equals("MAIN_REDACTOR")) {

                mail.setSubject("Obavestenje o prijavi novog rada");
                mail.setText("Pozdrav " + user.getName() + "! Obavestavamo Vas da je prijavljen novi rad u sistem.");

  //              javaMailSender.send(mail);

                System.out.println("Email glavnom uredniku poslat!");
            } else if(authority.getAuthority().equals("AUTHOR")){

                mail.setSubject("Obavestenje o prijavi Vaseg rada");
                mail.setText("Pozdrav " + user.getName() + "! Obavestavamo Vas da je Vas rad uspesno prijavljen u sistem.");

  //              javaMailSender.send(mail);

                System.out.println("Email autoru poslat!");
            }
        }

    }
}
