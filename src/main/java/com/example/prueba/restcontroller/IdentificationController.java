package com.example.prueba.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.prueba.entity.Identification;
import com.example.prueba.service.IdentificationService;

@RestController
@RequestMapping("/identification")
public class IdentificationController {

	@Autowired
	private IdentificationService identificationService;

	@PostMapping(value = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Identification> addIdentification(@RequestBody Identification identification) {
		Identification identificationSave = identificationService.save(identification);
		return new ResponseEntity<>(identificationSave, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Identification> updateIdentification(@PathVariable Long id,
			@RequestBody Identification identification) {
		Identification identificationSave = identificationService.update(id, identification);
		if (identificationSave != null)
			return new ResponseEntity<>(identificationSave, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Identification> getIdentification(@PathVariable Long id) {
		Identification identificationSearch = identificationService.findById(id);
		if (identificationSearch != null)
			return new ResponseEntity<>(identificationSearch, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/", produces="application/json")
    public ResponseEntity <List<Identification>>  getAllIdentifications() {
        List <Identification> identifications = identificationService.findAll();
		 
    return new ResponseEntity <>(identifications , HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Identification> deleteIdentification(@PathVariable Long id) {
		Identification identificationDelete = identificationService.delete(id);

		return new ResponseEntity<>(identificationDelete, HttpStatus.OK);
	}

}
