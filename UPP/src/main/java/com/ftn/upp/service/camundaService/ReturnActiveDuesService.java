package com.ftn.upp.service.camundaService;

import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnActiveDuesService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstanceId();
        String username = (String) delegateExecution.getVariable("loggedUsername");
        User user = this.userService.findUserByUsername(username);
        boolean isDuesActive = false;

        if(user != null){
            this.runtimeService.setVariable(processInstanceId,"isActiveDues", user.getDues().isActive());
            this.runtimeService.setVariable(processInstanceId,"dues", user.getDues());
        }


    }
}
