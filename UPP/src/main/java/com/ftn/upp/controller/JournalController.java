package com.ftn.upp.controller;

import com.ftn.upp.dto.FormFieldsDTO;
import com.ftn.upp.dto.MagazineDTO;
import com.ftn.upp.dto.NewJournalDTO;
import com.ftn.upp.model.Magazine;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/magazines")
public class JournalController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @GetMapping(value = "/startProcess")
    public ResponseEntity<FormFieldsDTO> getFormFields(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Obrada");

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    @GetMapping(value = "/getMagazines/{processInstanceId}")
    public ResponseEntity<List<MagazineDTO>> getMagazines(@PathVariable String processInstanceId){

        List<Magazine> magazines = (List<Magazine>) runtimeService.getVariable(processInstanceId,"magazines");

        List<MagazineDTO> magazineDTOS = new ArrayList<>();
        for(Magazine m: magazines){
            MagazineDTO magazineDTO = new MagazineDTO(m.getId(), m.getTitle());
            magazineDTOS.add(magazineDTO);
        }

        return new ResponseEntity(magazineDTOS, HttpStatus.OK);
    }


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
