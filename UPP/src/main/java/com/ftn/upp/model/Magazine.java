package com.ftn.upp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Table
@Entity(name = "MAGAZINE")
public class Magazine implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Date date;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "magazine_scientificFields",
            joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scientificField_id", referencedColumnName = "id"))
    private Set<ScientificField> scientificFields;

    @Column
    private String wayOfPayment;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

    @Column
    private boolean isActive;


    public Magazine() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<ScientificField> getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(Set<ScientificField> scientificFields) {
        this.scientificFields = scientificFields;
    }

    public String getWayOfPayment() {
        return wayOfPayment;
    }

    public void setWayOfPayment(String wayOfPayment) {
        this.wayOfPayment = wayOfPayment;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
