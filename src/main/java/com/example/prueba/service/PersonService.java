package com.example.prueba.service;

import java.util.List;

import com.example.prueba.entity.Person;


public interface PersonService {
    public Person findById(Long id);
    
    public List<Person> findAllPersons();
    
    public Person deletePerson(Long id);
    
    public Person save(Person person);
    
    public Person updatePerson(Long id,Person person);

}
