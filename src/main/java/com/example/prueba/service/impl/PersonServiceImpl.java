package com.example.prueba.service.impl;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.prueba.entity.Person;
import com.example.prueba.repository.PersonRepository;
import com.example.prueba.service.PersonService;


@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
  
	
	@Override
	public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<Person> findAllPersons() {
		return personRepository.findAll();
	}
	
	@Override
	public Person deletePerson(Long id) {
		Person person = findById(id);
	    person.setActive(false);
	    return personRepository.save(person);		
	}
	
	@Override
	public Person save(Person person) {
		Person p = new Person(person.getName(),person.getAge(),person.getIdentifications());
	
		return personRepository.save(p);
	}
	
	@Override
	public Person updatePerson(Long id, Person person) {
		Person persona = findById(id);
		
			persona.setName(person.getName());
			persona.setAge(person.getAge());
			persona.setGender(person.getGender());
			persona.setCode(person.getCode());
			persona.setActive(person.getActive());
			persona = personRepository.save(persona);
	
		return persona;
	}



}
