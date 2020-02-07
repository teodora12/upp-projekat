package com.ftn.upp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.upp.dto.FormSubmissionDTO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class User implements Serializable,UserDetails{

    private  static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String title;

    @Column
    private boolean isReviewer;

    @Column
    private boolean enabled;

    @Column
    private Timestamp lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "user_scientificFields",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "scientificField_id", referencedColumnName = "id"))
    private List<ScientificField> scientificFields;

    public User() {
        this.scientificFields = new ArrayList<>();
        this.authorities = new ArrayList<>();
    }

    public User(List<FormSubmissionDTO> registrationData){

        List<ScientificField> fields = new ArrayList<>();
        for(FormSubmissionDTO dto : registrationData) {

            if (dto.getFieldId().equals("name")) {
                this.name = dto.getFieldValue();
            } else if (dto.getFieldId().equals("lastname")) {
                this.lastname = dto.getFieldValue();
            } else if (dto.getFieldId().equals("city")) {
                this.city = dto.getFieldValue();
            } else if (dto.getFieldId().equals("state")) {
                this.country = dto.getFieldValue();
            } else if (dto.getFieldId().equals("email")) {
                this.email = dto.getFieldValue();
            } else if (dto.getFieldId().equals("username")) {
                this.username = dto.getFieldValue();
            } else if (dto.getFieldId().equals("reviewer")) {

                if (dto.getFieldValue() != null) {
                    if (dto.getFieldValue().equals("true")) {
                        this.isReviewer = true;
                    } else {
                        this.isReviewer = false;
                    }
                } else {
                    this.isReviewer = false;
                }
            }
        }

        this.enabled = false;

    }

    public List<ScientificField> getScientificFields() {
        return scientificFields;
    }

    public void setScientificFields(List<ScientificField> scientificFields) {
        this.scientificFields = scientificFields;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled(){
        return enabled;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
