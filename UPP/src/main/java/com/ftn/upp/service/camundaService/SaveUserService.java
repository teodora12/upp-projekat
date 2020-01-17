package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveUserService implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>) delegateExecution.getVariable("registration");
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String username = "";

        User user = null;

        for(FormSubmissionDTO dto: registration){
            if(dto.getFieldId().equals("name")){
                username = dto.getFieldValue();
                user = identityService.newUser(username);
            }
        }

        for(FormSubmissionDTO dto: registration){
            if(dto.getFieldId().equals("name")){
                firstName = dto.getFieldValue();
                user.setFirstName(firstName);
            } else if(dto.getFieldId().equals("lastname")){
                lastName = dto.getFieldValue();
                user.setLastName(lastName);
            } else if(dto.getFieldId().equals("email")){
                email = dto.getFieldValue();
                user.setEmail(email);
            } else if(dto.getFieldId().equals("password")){
                password = dto.getFieldValue();
                user.setPassword(password);
            }
        }

        identityService.saveUser(user);


    }
}
