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
public class FindRedactorService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> choosenMagazine = (List<FormSubmissionDTO>) delegateExecution.getVariable("choosenMagazine");

        FormSubmissionDTO title = new FormSubmissionDTO();
        for(FormSubmissionDTO formSubmissionDTO: choosenMagazine){
            if(formSubmissionDTO.getFieldId().equals("title")){
                title = formSubmissionDTO;
            }
        }
        Magazine magazine = this.magazineService.findMagazineByTitle(title.getFieldValue());
        User user = this.userService.findUserByAuthority("REDACTOR");

        for(User u: magazine.getUsers()){
            if(u.getUsername().equals(user.getUsername())){
                this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(),"redactorUsername",u.getUsername());
                this.userService.sendMailToRedactor(u);
            } else {
                String mainRedactorEmail = (String) delegateExecution.getVariable("mainRedactor");
                User mainRedactor = this.userService.findUserByEmail(mainRedactorEmail);
                this.userService.sendMailsAuthorRedactor(mainRedactor);

            }

        }




    }
}
