package com.ftn.upp.model;

import javax.persistence.*;

@Entity
@Table
public class ScientificField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
