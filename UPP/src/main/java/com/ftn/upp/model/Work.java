package com.ftn.upp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "WORK")
public class Work implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String abstr;

    @ManyToOne
    private ScientificField scientificField;

    @Column
    private String pdf;

    @ManyToMany
    private Set<KeyTerm> keyTerms;

    @ManyToMany
    private Set<User> users;

    public Work() {
        this.keyTerms = new HashSet<>();
        this.users = new HashSet<>();
    }




    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public ScientificField getScientificField() {
        return scientificField;
    }

    public void setScientificField(ScientificField scientificField) {
        this.scientificField = scientificField;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Set<KeyTerm> getKeyTerms() {
        return keyTerms;
    }

    public void setKeyTerms(Set<KeyTerm> keyTerms) {
        this.keyTerms = keyTerms;
    }
}
