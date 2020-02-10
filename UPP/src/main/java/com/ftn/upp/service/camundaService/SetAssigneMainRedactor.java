package com.ftn.upp.service.camundaService;

import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetAssigneMainRedactor implements TaskListener {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {

        String email = (String) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "mainRedactor");
        User user = this.userService.findUserByEmail(email);
        delegateTask.setAssignee(user.getUsername());

        System.out.println("izasao iz PostavljanjeAssignee");

    }
}
