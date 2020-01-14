package com.ftn.upp.controller;

import com.ftn.upp.dto.NewJournalDTO;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/journals")
public class JournalController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @GetMapping(value = "/getFormFields")
    public ResponseEntity<NewJournalDTO> startCreatingJournalProcess(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Journal");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity<>(new NewJournalDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    @PostMapping(value = "/post")
    public ResponseEntity<NewJournalDTO> blabla(){

        System.out.println("juishaiuhasi");

        return new ResponseEntity<>( HttpStatus.OK);
    }

}
