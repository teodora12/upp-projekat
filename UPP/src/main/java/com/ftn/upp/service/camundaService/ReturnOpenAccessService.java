package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.service.MagazineService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnOpenAccessService implements JavaDelegate {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private MagazineService magazineService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String processInstanceId = delegateExecution.getProcessInstanceId();
        List<FormSubmissionDTO> choosenMagazine = (List<FormSubmissionDTO>) delegateExecution.getVariable("choosenMagazine");

        FormSubmissionDTO title = new FormSubmissionDTO();
        for(FormSubmissionDTO dto: choosenMagazine){
            if(dto.getFieldId().equals("title")){
                title = dto;
            }
        }
        String magazineTitle = title.getFieldValue();
        Magazine magazine = this.magazineService.findMagazineByTitle(magazineTitle);
        boolean isOpenAccess = this.magazineService.isOpenAccess(magazine);
        this.runtimeService.setVariable(processInstanceId,"isOpenAccess",isOpenAccess);

    }
}
