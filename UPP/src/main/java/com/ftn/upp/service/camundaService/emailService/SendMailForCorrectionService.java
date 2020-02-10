package com.ftn.upp.service.camundaService.emailService;

import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMailForCorrectionService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String authorUsername = (String) delegateExecution.getVariable("loggedUsername");
        User author = this.userService.findUserByUsername(authorUsername);

        String comment = (String) delegateExecution.getVariable("stringComment");

        this.userService.sendMailToAuthorForCorrection(author,comment);


    }
}
