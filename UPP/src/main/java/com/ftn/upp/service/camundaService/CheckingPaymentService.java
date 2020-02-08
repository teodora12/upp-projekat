package com.ftn.upp.service.camundaService;

import com.ftn.upp.model.Dues;
import com.ftn.upp.model.User;
import com.ftn.upp.service.DuesService;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingPaymentService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private DuesService duesService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Dues duesVariable = (Dues) delegateExecution.getVariable("dues");
        String username = (String) delegateExecution.getVariable("loggedUsername");

        User user = this.userService.findUserByUsername(username);
        Dues dues = this.duesService.findDuesById(duesVariable.getId());
        if(user != null && dues != null){
            dues.setActive(true);
            user.setDues(dues);
            this.duesService.saveDues(dues);
            this.userService.saveUser(user);
        }


    }
}
