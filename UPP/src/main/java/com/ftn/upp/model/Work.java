package com.ftn.upp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "WORK")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String theme;

    @Column
    private String abstr;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ScientificField scientificField;

    @Column
    private String pdf;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<KeyTerm> keyTerms;

    public Work() {
        this.keyTerms = new HashSet<>();
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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
