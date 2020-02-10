package com.ftn.upp.dto;

import java.util.List;

public class FormSubmissionWithFileDTO {

    private List<FormSubmissionDTO> form;
    private String file;
    private String fileName;

    public FormSubmissionWithFileDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FormSubmissionWithFileDTO(List<FormSubmissionDTO> form, String file, String fileName) {
        super();
        this.form = form;
        this.file = file;
        this.fileName = fileName;
    }

    public List<FormSubmissionDTO> getForm() {
        return form;
    }

    public void setForm(List<FormSubmissionDTO> form) {
        this.form = form;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
