package com.ftn.upp.service.camundaService;

import com.ftn.upp.model.Magazine;
import com.ftn.upp.service.MagazineService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReturnMagazinesService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstance().getId();
        List<Magazine> magazines = this.magazineService.findAll();
        this.runtimeService.setVariable(processInstanceId,"magazines", magazines);

    }
}
