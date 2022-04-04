package com.example.prueba.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Data;


@Entity
@Data
public class Person implements Serializable{
	   

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long idPerson;
    
    @Column(name="name")
    private String name;
    
    @Column(name="age")
    private int age;
    
    @Column(name="gender")
    private String gender;    
    
    @Column(name="code")
    private String code;  
    
    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    private Date  createdAt; 
    
    @Column(name="active")
    private Boolean active = true;

    @JsonIgnoreProperties(value = {"persons","hibernateLazyInitializer","handler"},allowSetters = true)
    @ManyToMany(fetch = FetchType.LAZY,cascade = {
            
            CascadeType.MERGE
    })
    @JoinTable(name = "person_identification",
            joinColumns = @JoinColumn(name = "id_identification"),
            inverseJoinColumns = @JoinColumn(name = "id_person")
    )
    private List<Identification> identifications = new ArrayList<>();
    
    @PrePersist
    public void addCreatedAt(){
    	this.createdAt = new Date();
    	this.code = RandomStringUtils.randomAlphanumeric(15).toUpperCase();
    }

    
    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
       
    }
    
    public Person(@NotNull String name,@NotNull int age,List <Identification> identifications) {
       this.name = name;
       this.age = age;
       this.identifications = identifications;
       this.gender = "";
    }
    
    public Person(@NotNull String name,@NotNull int age) {
        this.name = name;
        this.age = age;
        this.gender = "";
     }
    
    public Person() {
    	
    }
}

