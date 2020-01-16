package com.ftn.upp.dto;

import org.camunda.bpm.engine.form.FormField;

import javax.persistence.Column;
import java.util.List;

public class RegisterDTO {

    private String name;

    private String lastname;

    private String email;

    private String username;

    private String password;

    private String state;

    private String city;

    private String title;

    private boolean isReviewer;

    private long numOfScientificFields;

    public RegisterDTO() {
    }

    public RegisterDTO(String name, String lastname, String email, String password, String username,
                       String state, String city, String title, boolean isReviewer, long numOfScientificFields) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.state = state;
        this.city = city;
        this.title = title;
        this.isReviewer = isReviewer;
        this.numOfScientificFields = numOfScientificFields;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isReviewer() {
        return isReviewer;
    }

    public void setReviewer(boolean reviewer) {
        isReviewer = reviewer;
    }

    public long getNumOfScientificFields() {
        return numOfScientificFields;
    }

    public void setNumOfScientificFields(long numOfScientificFields) {
        this.numOfScientificFields = numOfScientificFields;
    }
}
