package com.ftn.upp.service.camundaService;

import com.ftn.upp.model.User;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorrectingWorkTask implements TaskListener {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;


    @Override
    public void notify(DelegateTask delegateTask) {

        String authorUsername = (String) runtimeService.getVariable(delegateTask.getProcessInstanceId(), "loggedUsername");
  //      User author = this.userService.findUserByUsername(authorUsername);
        delegateTask.setAssignee(authorUsername);

        System.out.println("izasao iz dodele taska za ispravljanje pdfa autoru!!!!!!!!");


    }
}
