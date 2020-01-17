package com.ftn.upp.controller;

import com.ftn.upp.dto.FormFieldsDTO;
import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.model.User;
import com.ftn.upp.service.ScientificFieldService;
import com.ftn.upp.service.UserService;
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

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScientificFieldService scientificFieldService;

    @GetMapping(value = "/startProcess")
    public ResponseEntity<FormFieldsDTO> getFieldsForRegistration(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Registration");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    @PostMapping(value = "/activate/{taskId}")
    public ResponseEntity activateAccount(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String , Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "activationOfUserAccount", dto);
        formService.submitTaskForm(taskId,map);

        return ResponseEntity.ok().build();

    }

    @PostMapping(value = "/setReviewer/{taskId}")
    public ResponseEntity submitReviewer(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {
        HashMap<String , Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "sumbmissionOfReviewer", dto);
        formService.submitTaskForm(taskId,map);

        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/admin/getRequestsForReviewers")
    public ResponseEntity getReviewers(){
        Task task = taskService.createTaskQuery().taskCandidateGroup("demo").list().get(0);
//		Task task = taskService.createTaskQuery().processInstanceId(procesInstanceId).taskCandidateGroup("demo").list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }


        return new ResponseEntity(new FormFieldsDTO(task.getId(), task.getProcessInstanceId(), properties),HttpStatus.OK);
    }

    @PostMapping(value = "/register/{taskId}")
    public ResponseEntity register(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){
        HashMap<String , Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
        formService.submitTaskForm(taskId,map);

        long numOfScientificFields = 1;
        for(FormSubmissionDTO num : dto){
            if(num.getFieldId().equals("scientificFields")){
                numOfScientificFields = Long.parseLong(num.getFieldValue());
            }
        }
        return new ResponseEntity(numOfScientificFields,HttpStatus.OK);
    }
    @PostMapping(value = "/postScientificField/{taskId}")
    public ResponseEntity postScientificField(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){
        HashMap<String , Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();

        FormSubmissionDTO scientificFieldNameDTO = new FormSubmissionDTO();
        for(FormSubmissionDTO dto1: dto){
            if(dto1.getFieldId().equals("scientificField")){
                scientificFieldNameDTO = dto1;
            }
        }

        ScientificField scientificField = this.scientificFieldService.findScientificFieldByName(scientificFieldNameDTO.getFieldValue());
        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>) runtimeService.getVariable(task.getExecutionId(),"registration");


        String username = null;
        for(FormSubmissionDTO dto1: registration){
            if(dto1.getFieldId().equals("username")){
                username = dto1.getFieldValue();
            }
        }
        User user = this.userService.findUserByUsername(username);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        if(scientificField == null){
            ScientificField scientificFieldNew = new ScientificField();
            scientificFieldNew.setName(scientificFieldNameDTO.getFieldValue());
            this.scientificFieldService.save(scientificFieldNew);
            user.getScientificFields().add(scientificFieldNew);
            this.userService.saveUser(user);
        }

        user.getScientificFields().add(scientificField);
        this.userService.saveUser(user);

        formService.submitTaskForm(taskId,map);
        return ResponseEntity.ok().build();


    }
/*    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerDTO) {


        User user = this.userService.register(registerDTO);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);


    }*/

    @GetMapping(value = "/getScientificFieldForm/{processInstanceId}")
    public ResponseEntity<FormFieldsDTO> getFieldsForScientificField(@PathVariable String processInstanceId){


        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstanceId,fields), HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);

        return user;
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

}
