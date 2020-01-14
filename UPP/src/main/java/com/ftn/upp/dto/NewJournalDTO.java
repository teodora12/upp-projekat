package com.ftn.upp.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.ArrayList;
import java.util.List;

public class NewJournalDTO {

    String taskId;
    String processInstanceId;
    List<FormField> formFields;

    public NewJournalDTO() {
    }

    public NewJournalDTO(String taskId, String processInstanceId, List<FormField> formFields) {
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.formFields = formFields;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }
}
