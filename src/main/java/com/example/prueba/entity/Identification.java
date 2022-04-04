package com.example.prueba.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="identification")
public class Identification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_identification")
    private Long idIdentification;
	
    @Column(name="identification_name")
    private String identificationName;
    
    @Column(name="description")
    private String description;
   
    @JsonIgnoreProperties(allowSetters = true, value = {"createdAt"})
    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;    
    
    @JsonIgnoreProperties(allowSetters = true, value = {"active"})
    @Column(name="active")
    private Boolean active = true;
    
    @JsonIgnoreProperties(value = {"identifications","hibernateLazyInitializer","handler"},allowSetters = true)
    @ManyToMany(mappedBy = "identifications")
    public List<Person> persons = new ArrayList<>();
    
    @PrePersist
    public void addCreatedAt() {
    	this.createdAt = new Date();
    }
    
    public Identification(Long idIdentification,String identificationName, String description) {
    	this.idIdentification = idIdentification;
    	this.identificationName = identificationName;
        this.description = description;
    }
    
    public Identification(String identificationName, String description) {
        this.identificationName = identificationName;
        this.description = description;
    }
    

    public Identification() {
    	
    }

}
