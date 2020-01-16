package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.repository.UserRepository;
import com.ftn.upp.service.AuthorityService;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SaveRegistrationSubmissionData implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>) delegateExecution.getVariable("registration");

        FormSubmissionDTO dto = new FormSubmissionDTO();
        for (FormSubmissionDTO dto1: registration){
            if(dto1.getFieldId().equals("username")){
                dto = dto1;
            }
        }

        List<User> allUsers = this.userService.findAll();
        boolean exsist = false;
//        User user = this.userService.findUserByUsername(dto.getFieldValue());
        for(User user : allUsers){
            if(user.getUsername().equals(dto.getFieldValue())){
                exsist = true;
            }
        }

        if(exsist == false){
            User newUser = new User(registration);
            Authority authority = this.authorityService.findAuthorityByName("USER_ROLE");
            List<Authority> authorities = Arrays.asList(authority);
            newUser.setAuthorities(authorities);
            for(FormSubmissionDTO dto2: registration){
                if(dto2.getFieldId().equals("password")){
                    newUser.setPassword(passwordEncoder.encode(dto2.getFieldValue()));
                }
            }

            this.userService.saveUser(newUser);

        }



    }

}
