package com.ftn.upp.controller;

import com.ftn.upp.dto.*;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.service.MagazineService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/api/magazines")
public class JournalController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private MagazineService magazineService;

    @GetMapping(value = "/startProcess")
    public ResponseEntity<FormFieldsDTO> getFormFields(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Obrada");

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    @GetMapping(value = "/getFormFields/{processInstanceId}")
    public ResponseEntity<FormFieldsDTO> getFormFields(@PathVariable String processInstanceId){
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstanceId,fields), HttpStatus.OK);
    }

    @GetMapping(value = "/getScientificFieldsForMagazine/{magazineTitle}")
    public ResponseEntity<List<ScientificFieldDTO>> getScientificFieldsForMagazine(@PathVariable String magazineTitle){

        Magazine magazine = this.magazineService.findMagazineByTitle(magazineTitle);
        List<ScientificFieldDTO> scientificFields = new ArrayList<>();
        for(ScientificField scientificField: magazine.getScientificFields()){
            ScientificFieldDTO scientificFieldDTO = new ScientificFieldDTO(scientificField);
            scientificFields.add(scientificFieldDTO);
        }

        return new ResponseEntity<>(scientificFields,HttpStatus.OK);
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

    @PostMapping(value = "/submitChoosenMagazine/{taskId}")
    public ResponseEntity submitChoosenMagazine(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){
        HashMap<String , Object> map = this.mapListToDto(dto);



        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "choosenMagazine", dto);
        formService.submitTaskForm(taskId,map);

        boolean isOpenAccess = (boolean) this.runtimeService.getVariable(processInstanceId,"isOpenAccess");

        return new ResponseEntity(isOpenAccess,HttpStatus.OK);

    }

    @GetMapping(value = "/getFormFields")
    public ResponseEntity<NewJournalDTO> startCreatingJournalProcess(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Journal");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity<>(new NewJournalDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }


}
