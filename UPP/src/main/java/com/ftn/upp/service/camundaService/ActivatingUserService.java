package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivatingUserService implements JavaDelegate {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>) delegateExecution.getVariable("registration");

        FormSubmissionDTO usernameDTO = new FormSubmissionDTO();
        for(FormSubmissionDTO dto: registration){
            if(dto.getFieldId().equals("username")){
                usernameDTO = dto;
            }
        }
        User user = this.userService.findUserByUsername(usernameDTO.getFieldValue());
        if(user != null){
            user.setEnabled(true);
            this.userService.saveUser(user);
        }

    }
}
