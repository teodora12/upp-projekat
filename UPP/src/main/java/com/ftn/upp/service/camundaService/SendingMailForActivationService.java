package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendingMailForActivationService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> dtoList = (List<FormSubmissionDTO>) delegateExecution.getVariable("registration");
        FormSubmissionDTO emailDTO = new FormSubmissionDTO();
        for(FormSubmissionDTO dto: dtoList){
            if(dto.getFieldId().equals("email")){
                emailDTO = dto;
            }
        }

        String processInstanceId = delegateExecution.getProcessInstanceId();

        User user = this.userService.findUserByEmail(emailDTO.getFieldValue());

        this.userService.SendMailForActivation(user,processInstanceId);


    }
}
