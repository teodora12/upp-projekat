package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.User;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChooseMainRedactorService implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> choosenMagazine = (List<FormSubmissionDTO>) delegateExecution.getVariable("choosenMagazine");

        FormSubmissionDTO title = new FormSubmissionDTO();
        for(FormSubmissionDTO dto: choosenMagazine){
            if(dto.getFieldId().equals("title")){
                title = dto;
            }
        }
        String magazineTitle = title.getFieldValue();
        Magazine magazine = this.magazineService.findMagazineByTitle(magazineTitle);

        User user = this.userService.findUserByAuthority("MAIN_REDACTOR");
        for(User u: magazine.getUsers()){
            if(u.getEmail().equals(user.getEmail())){
                this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(),"mainRedactor",u.getEmail());
            }
        }


    }
}
