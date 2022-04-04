package com.example.prueba.restcontroller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.prueba.entity.Person;
import com.example.prueba.service.PersonService;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping(value = "/",consumes="application/json", produces="application/json")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person p = personService.save(person);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }
	

	
	@PutMapping("/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person){
		Person p = personService.updatePerson(id,person);
    	return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces="application/json")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        Person p = personService.findById(id); 
    if(p != null) return new ResponseEntity<>(p, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
	
	@GetMapping(value = "/", produces="application/json")
    public ResponseEntity <List<Person>>  getAllPersons() {
        List <Person> p = personService.findAllPersons();
		 
        return new ResponseEntity <>(p, HttpStatus.OK);
      
    }
	
	@DeleteMapping(value = "/{id}", produces="application/json")
    public ResponseEntity <Person>  deletePerson(@PathVariable Long id) {
        Person p = personService.deletePerson(id);
		
     return new ResponseEntity <>(p, HttpStatus.OK);
       
    }

	
}
