package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.Work;
import com.ftn.upp.service.WorkService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveCorrectedWorkService implements JavaDelegate {

    @Autowired
    private WorkService workService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> dto = (List<FormSubmissionDTO>) delegateExecution.getVariable("workCorrection");
        List<FormSubmissionDTO> workData = (List<FormSubmissionDTO>) delegateExecution.getVariable("workData");

        String titleWork = "";
        for(FormSubmissionDTO wd: workData){
            if(wd.getFieldId().equals("title")){
                titleWork = wd.getFieldValue();
            }
        }

        Work work = this.workService.findWorkByTitle(titleWork);

        FormSubmissionDTO dto1 = new FormSubmissionDTO();
        for(FormSubmissionDTO dto2: dto){
            if(dto2.getFieldId().equals("titleCorrection")){
                work.setTitle(dto2.getFieldValue());
            } else if(dto2.getFieldId().equals("abstractCorrection")){
                work.setAbstr(dto2.getFieldValue());
            } else if(dto2.getFieldId().equals("keyTermsCorrection")){
    //            work.getKeyTerms().add()

            }
        }

        this.workService.saveWork(work);

    }
}
