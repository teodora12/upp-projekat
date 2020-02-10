package com.ftn.upp.controller;

import com.ftn.upp.dto.*;
import com.ftn.upp.model.Magazine;
import com.ftn.upp.model.ScientificField;
import com.ftn.upp.model.User;
import com.ftn.upp.model.Work;
import com.ftn.upp.repository.WorkRepository;
import com.ftn.upp.security.TokenUtils;
import com.ftn.upp.service.MagazineService;
import com.ftn.upp.service.UserService;
import com.ftn.upp.service.WorkService;
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
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private TokenUtils tokenUtils;

    @Autowired
    private WorkService workService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkRepository workRepository;

    @GetMapping(value = "/startProcess/{username}")
    public ResponseEntity<FormFieldsDTO> getFormFieldsAndStartProcess(@PathVariable String username){

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_Obrada");
        this.runtimeService.setVariable(processInstance.getId(),"loggedUsername", username);
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstance.getId(),fields), HttpStatus.OK);
    }

    @GetMapping(value = "/getFormFields/{processInstanceId}")
    public ResponseEntity<FormFieldsDTO> getFormFields(@PathVariable String processInstanceId){
        String username = (String) this.runtimeService.getVariable(processInstanceId, "loggedUsername");

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        task.setAssignee(username);
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

     //   taskService.complete(task.getId());

        return new ResponseEntity(isOpenAccess,HttpStatus.OK);

    }

    @PostMapping(value = "/submitWorkData/{taskId}")
    public ResponseEntity submitWorkData(@RequestBody FormSubmissionWithFileDTO dto, @PathVariable String taskId) throws IOException {
        HashMap<String, Object> map = this.mapListToDto(dto.getForm());

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"workData",dto.getForm());
        runtimeService.setVariable(processInstanceId,"pdfName",dto.getFileName());
        formService.submitTaskForm(taskId,map);

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());
        runtimeService.setVariable(processInstanceId, "pdfRad", decodedBytes);

        File file = new File("src/main/pdf/" + dto.getFileName());
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();


   //     taskService.complete(task.getId());

        long numOfCoauthors = 1;
        for(FormSubmissionDTO num : dto.getForm()){
            if(num.getFieldId().equals("numOfCoauthors")){
                numOfCoauthors = Long.parseLong(num.getFieldValue());
            }
        }

        return new ResponseEntity(numOfCoauthors,HttpStatus.OK);

    }

    @PostMapping(value = "/submitCoauthor/{taskId}")
    public ResponseEntity submitCoauthor(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        String  processInstanceId = task.getProcessInstanceId();

        List<FormSubmissionDTO> workData = (List<FormSubmissionDTO>) this.runtimeService.getVariable(processInstanceId,"workData");
        List<FormSubmissionDTO> choosenMagazine = (List<FormSubmissionDTO>) this.runtimeService.getVariable(processInstanceId,"choosenMagazine");

        FormSubmissionDTO title = new FormSubmissionDTO();
        for(FormSubmissionDTO formSubmissionDTO: choosenMagazine){
            if(formSubmissionDTO.getFieldId().equals("title")){
                title = formSubmissionDTO;
            }
        }
        String magazineTitle = title.getFieldValue();
        Magazine magazine = this.magazineService.findMagazineByTitle(magazineTitle);

        FormSubmissionDTO worktitle = new FormSubmissionDTO();
        for(FormSubmissionDTO formSubmissionDTO: workData){
            if(formSubmissionDTO.getFieldId().equals("title")){
                worktitle = formSubmissionDTO;
            }
        }
        String workTitle = worktitle.getFieldValue();
        Work work = this.workService.findWorkByTitle(workTitle);

/*        for(FormSubmissionDTO dto1: dto) {
            if (dto1.getFieldId().equals("coauthorEmail")) {
                User exsistingUser = this.userService.findUserByEmail(dto1.getFieldValue());
                if(exsistingUser == null){

                }
            }
        }*/

        User user = new User();
        user.setEnabled(false);

        FormSubmissionDTO field = new FormSubmissionDTO();
        for(FormSubmissionDTO f: dto){
            if (f.getFieldId().equals("coauthorName")) {
                user.setName(f.getFieldValue());
            } else if (f.getFieldId().equals("coauthorLastName")) {
                user.setLastname(f.getFieldValue());
            } else if (f.getFieldId().equals("coauthorCity")) {
                user.setCity(f.getFieldValue());
            } else if (f.getFieldId().equals("coauthorCountry")) {
                user.setCountry(f.getFieldValue());
            } else if (f.getFieldId().equals("coauthorEmail")) {
                user.setEmail(f.getFieldValue());
            }
        }
        this.userService.saveUser(user);
        work.getUsers().add(user);
        this.workService.saveWork(work);
        this.magazineService.saveMagazine(magazine);
        formService.submitTaskForm(taskId,map);


        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/getFormFieldsPayment/{processInstanceId}")
    public ResponseEntity<FormFieldsDTO> getFormFieldsPayment(@PathVariable String processInstanceId){

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
//        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateGroup("mainRedactor1").list().get(0);

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> fields = taskFormData.getFormFields();

        return new ResponseEntity(new FormFieldsDTO(task.getId(),processInstanceId,fields), HttpStatus.OK);
    }

    @GetMapping(value = "/getFormFieldsMRedactor/{processInstanceId}")
    public ResponseEntity<FormFieldsDTO> getFormFieldsMRedactor(@PathVariable String processInstanceId){

  //      processInstanceId = processInstanceId.substring(1, processInstanceId.length() - 1);
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

  //      String email = (String) runtimeService.getVariable(processInstanceId, "mainRedactor");
  //      User user = this.userService.findUserByEmail(email);
   //     List<Task> tasks = taskService.createTaskQuery().taskAssignee(user.getUsername()).taskName("CheckWorkDataTask").list();
 //       Task task = taskService.createTaskQuery().taskAssignee("mainRedactor1").taskName("CheckWorkDataTask").list().get(0);

        FormFieldsDTO formFieldsDto = new FormFieldsDTO();
        formFieldsDto.setTaskId(task.getId());
        formFieldsDto.setProcessInstanceId(task.getProcessInstanceId());
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        formFieldsDto.setFormFields(properties);

//        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
//        List<FormField> fields = taskFormData.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
            System.out.println(fp.getDefaultValue());
        }

        return new ResponseEntity(formFieldsDto, HttpStatus.OK);
    }

    @PostMapping(value = "/submitPayment/{taskId}")
    public ResponseEntity submitPayment(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        formService.submitTaskForm(task.getId(),map);
  //      taskService.complete(task.getId());

        return ResponseEntity.ok().build();

    }


    @GetMapping(value = "/isActiveDues/{processInstanceId}")
    public ResponseEntity<Boolean> isActiveDues(@PathVariable String processInstanceId){

        String username = (String) this.runtimeService.getVariable(processInstanceId,"loggedUsername");
        User user = this.userService.findUserByUsername(username);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        boolean isActiveMembership = user.getDues().isActive();

        return new ResponseEntity<>(isActiveMembership,HttpStatus.OK);

    }

    @PostMapping(value = "/submitMainRedactorOverview/{taskId}")
    public ResponseEntity submitMainRedactorOverview(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId){

        HashMap<String, Object> map = this.mapListToDto(dto);

        FormSubmissionDTO isAcceptedField = new FormSubmissionDTO();
        for(FormSubmissionDTO fsd: dto){
            if(fsd.getFieldId().equals("accept")){
                isAcceptedField = fsd;
            }
        }

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();

        boolean isAccepted = false;
        if(isAcceptedField.getFieldValue().equals("true")) {
            isAccepted = true;
            this.runtimeService.setVariable(task.getProcessInstanceId(),"accept", isAccepted);
        }else {
            this.runtimeService.setVariable(task.getProcessInstanceId(), "accept", isAccepted);
        }
        formService.submitTaskForm(task.getId(),map);

        return new ResponseEntity(isAccepted,HttpStatus.OK);

    }

    @PostMapping(value = "/submitMainRedactorAcceptPdf/{taskId}")
    public ResponseEntity submitMainRedactorAcceptPdf(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        FormSubmissionDTO isAcceptedField = new FormSubmissionDTO();
        for(FormSubmissionDTO fsd: dto){
            if(fsd.getFieldId().equals("acceptPDF")){
                isAcceptedField = fsd;
            }
        }
        boolean isAcceptedPdf = false;
        if(isAcceptedField.getFieldValue().equals("true")) {
            isAcceptedPdf = true;
        }

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        this.runtimeService.setVariable(task.getProcessInstanceId(),"acceptPDF", isAcceptedPdf);

        formService.submitTaskForm(task.getId(),map);

        return new ResponseEntity(isAcceptedPdf,HttpStatus.OK);

    }


    @PostMapping(value = "/submitCommentMainRedactor/{taskId}")
    public ResponseEntity submitCommentMainRedactor(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        FormSubmissionDTO commentPDFdeclined = new FormSubmissionDTO();
        for(FormSubmissionDTO fsd: dto){
            if(fsd.getFieldId().equals("commentPDFdeclined")){
                commentPDFdeclined = fsd;
            }
        }
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();

        this.runtimeService.setVariable(task.getProcessInstanceId(),"stringComment",commentPDFdeclined.getFieldValue());

        formService.submitTaskForm(task.getId(),map);

        return new ResponseEntity(HttpStatus.OK);


    }

    @GetMapping(value = "/getWorkForCorrection/{processInstanceId}")
    public ResponseEntity<FormFieldsDTO> getWorkForCorrection(@PathVariable String processInstanceId) {

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);

        FormFieldsDTO formFieldsDto = new FormFieldsDTO();
        formFieldsDto.setTaskId(task.getId());
        formFieldsDto.setProcessInstanceId(task.getProcessInstanceId());
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        formFieldsDto.setFormFields(properties);


        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
            System.out.println(fp.getDefaultValue());
        }

        return new ResponseEntity(formFieldsDto, HttpStatus.OK);

    }

    @PostMapping(value = "/submitCorrection/{taskId}")
    public ResponseEntity submitCorrection(@RequestBody List<FormSubmissionDTO> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        this.runtimeService.setVariable(task.getProcessInstanceId(),"workCorrection",dto);
        formService.submitTaskForm(task.getId(),map);

        return new ResponseEntity(HttpStatus.OK);


    }
















        @GetMapping(value = "/getWorks/{processInstanceId}")
    public ResponseEntity getWorks(@PathVariable String processInstanceId) {

        String username = (String) this.runtimeService.getVariable(processInstanceId, "loggedUsername");

        User user = this.userService.findUserByUsername(username);
//        List<Work> works = this.workRepository.findWorksByUsers(user);

        Work work = this.workRepository.findWorkByUsers(user);

        Work work1 = this.workRepository.findWorkByTitle("naslov rada");

        return new ResponseEntity(work,HttpStatus.OK);
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
