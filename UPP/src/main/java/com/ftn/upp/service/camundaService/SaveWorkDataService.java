package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.KeyTerm;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.model.Work;
import com.ftn.upp.service.KeyTermService;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.ScientificFieldService;
import com.ftn.upp.service.WorkService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveWorkDataService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private WorkService workService;

    @Autowired
    private KeyTermService keyTermService;

    @Autowired
    private ScientificFieldService scientificFieldService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> workData = (List<FormSubmissionDTO>) delegateExecution.getVariable("workData");

        List<FormSubmissionDTO> choosenMagazine = (List<FormSubmissionDTO>) delegateExecution.getVariable("choosenMagazine");

        FormSubmissionDTO title = new FormSubmissionDTO();
        for(FormSubmissionDTO dto: choosenMagazine){
            if(dto.getFieldId().equals("title")){
                title = dto;
            }
        }
        String magazineTitle = title.getFieldValue();
        Magazine magazine = this.magazineService.findMagazineByTitle(magazineTitle);

        Work work = new Work();
        FormSubmissionDTO field = new FormSubmissionDTO();
        for(FormSubmissionDTO dto: workData){
            if(dto.getFieldId().equals("title")){
                work.setTitle(dto.getFieldValue());
            } else if (dto.getFieldId().equals("theme")){
                work.setTheme(dto.getFieldValue());
            } else if (dto.getFieldId().equals("abstract")){
                work.setAbstr(dto.getFieldValue());
            } else if (dto.getFieldId().equals("pdf")){
                work.setPdf(dto.getFieldValue());
            } else if (dto.getFieldId().equals("keyTerms")){
                String[] forSplit = dto.getFieldValue().split(",");
                for(String str: forSplit){
                    KeyTerm keyTerm = new KeyTerm();
                    keyTerm.setName(str);
                    work.getKeyTerms().add(keyTerm);
                    this.keyTermService.saveKeyTerm(keyTerm);
                }
            } else if(dto.getFieldId().equals("scientificFieldName")){
                ScientificField scientificField = this.scientificFieldService.findScientificFieldByName(dto.getFieldValue());
                work.setScientificField(scientificField);
            }
            this.workService.saveWork(work);
            magazine.getWorks().add(work);
            this.magazineService.saveMagazine(magazine);


        }


    }

}
