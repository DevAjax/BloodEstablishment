package com.springbootapp.models;


import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
