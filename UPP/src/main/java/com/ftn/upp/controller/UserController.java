package com.ftn.upp.controller;

import com.ftn.upp.dto.FormFieldsDTO;
import com.ftn.upp.dto.FormSubmissionDTO;
import com.ftn.upp.dto.RegisterDTO;
import com.ftn.upp.model.User;
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

    @GetMapping(value = "/startProcess")
    public ResponseEntity<FormFieldsDTO> getFieldsForRegistration(){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Registration");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    @PostMapping(value = "/register/{taskId}")
    public ResponseEntity register(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){

        HashMap<String , Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
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

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

}
