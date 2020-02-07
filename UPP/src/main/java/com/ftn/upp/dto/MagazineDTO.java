package com.ftn.upp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MagazineDTO {

    private Long id;
    private String title;
    private List<ScientificFieldDTO> scientificFields;

    public MagazineDTO(){
        this.scientificFields = new ArrayList<>();
    }

    public MagazineDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }


    public MagazineDTO(Long id, String title, List<ScientificFieldDTO> scientificFields) {
        this.id = id;
        this.title = title;
        this.scientificFields = scientificFields;
    }

    public List<ScientificFieldDTO> getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(List<ScientificFieldDTO> scientificFields) {
        this.scientificFields = scientificFields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
