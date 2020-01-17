package com.ftn.upp.service.camundaService;

import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.model.Authority;
import com.ftn.upp.model.User;
import com.ftn.upp.service.AuthorityService;
import com.ftn.upp.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class SaveUserAsReviewerService implements JavaDelegate {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>) delegateExecution.getVariable("registration");
        System.out.println(registration);
        FormSubmissionDTO usernameDTO = null;
        for (FormSubmissionDTO dto : registration) {
            if (dto.getFieldId().equals("username")) {
                usernameDTO = dto;
            }
        }

        User user =  this.userService.findUserByUsername(usernameDTO.getFieldValue());
//        user.getAuthorities().clear();
//        Authority authority = this.authorityService.findAuthorityByName("REVIEWER");
//        List<Authority> authorities = Arrays.asList(authority);
//        user.setAuthorities(authorities);

        user.setReviewer(true);
        this.userService.saveUser(user);
    }
}
