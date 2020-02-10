package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnAcceptService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstanceId();
        boolean accept = (boolean) this.runtimeService.getVariable(delegateExecution.getProcessInstanceId(),"accept");
        System.out.println(accept + "PROVERI PROCESNU VARIJABLU ACCEPT");

    }
}
