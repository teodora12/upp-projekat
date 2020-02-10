package com.ftn.upp.service.camundaService.emailService;

import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkAcceptedSendEmailService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String mainRedactorEmail = (String) delegateExecution.getVariable("mainRedactor");

        User user = this.userService.findUserByEmail(mainRedactorEmail);

        this.userService.sendPdfInMail(user);

    }
}
