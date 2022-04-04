package com.example.prueba.restcontroller;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.prueba.entity.Identification;
import com.example.prueba.entity.Person;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {

	@Autowired
	private TestRestTemplate client;

	@LocalServerPort
	private int puerto;

	// contructor name and age
	@Test
	@Order(1)
	void testAddPerson() {
		Person person = new Person("oscar", 27);

		ResponseEntity<Person> respuesta = client.postForEntity(crearUri("/person/"), person, Person.class);
		assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		Person personCreado = respuesta.getBody();
		assertNotNull(personCreado);
		assertEquals("oscar", personCreado.getName());
		assertEquals("", personCreado.getGender());
	}

	// constructor con lista de identificaciones
	@Test
	@Order(2)
	void testAddPerson2() {

		List<Identification> IDENTIFICATIONS = Arrays.asList(new Identification(1L, "INE", "INE"),
				new Identification(2L, "PASAPORTE", "PASAPORTE"));

		List<Identification> Identifications = IDENTIFICATIONS;
		Person person = new Person("oscar", 27, Identifications);
		ResponseEntity<Person> respuesta = client.postForEntity(crearUri("/person/"), person, Person.class);
		assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		Person personCreado = respuesta.getBody();
		assertNotNull(personCreado);
		assertEquals("oscar", personCreado.getName());
		assertEquals("", personCreado.getGender());
		assertEquals(2, personCreado.getIdentifications().size());
	}

	@Test
	@Order(3)
	void testGetAllPersons() {

		ResponseEntity<Person[]> respuesta = client.getForEntity(crearUri("/person/"), Person[].class);
		List<Person> persons = Arrays.asList(respuesta.getBody());
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

	}

	@Test
	@Order(4)
	void testGetPerson() {
		ResponseEntity<Person> respuesta = client.getForEntity(crearUri("/person/1"), Person.class);
		Person person = respuesta.getBody();
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

		assertNotNull(person);
		assertEquals(1L, person.getIdPerson());

	}

	@Test
	@Order(5)
	void testGetPersonNotExists() {
		ResponseEntity<Person> respuesta = client.getForEntity(crearUri("/person/100"), Person.class);
		Person person = respuesta.getBody();
		assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
		assertNull(person);

	}

	@Test
	@Order(6)
	void testDeletePerson() {
		Map<String, Long> pathVariables = new HashMap<>();
		pathVariables.put("id", 1L);
		ResponseEntity<Person> exchange = client.exchange(crearUri("/person/{id}"), HttpMethod.DELETE, null,
				Person.class, pathVariables);

		assertEquals(HttpStatus.OK, exchange.getStatusCode());
		assertTrue(exchange.hasBody());
		assertEquals(false, exchange.getBody().getActive());

	}

	@Test
	@Order(7)
	void testUpdatePerson() {

		ResponseEntity<Person> respuesta = client.getForEntity(crearUri("/person/1"), Person.class);
		Person person = respuesta.getBody();

		person.setName("oscar updt2");

		HttpEntity<Person> personEntity = new HttpEntity<Person>(person);
		ResponseEntity<Person> exchange = client.exchange(crearUri("/person/1"), HttpMethod.PUT, personEntity,
				Person.class);

		assertEquals(HttpStatus.OK, exchange.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, exchange.getHeaders().getContentType());
		assertNotNull(exchange.getBody());
		assertEquals("oscar updt2", exchange.getBody().getName());

	}

	private String crearUri(String uri) {
		return "http://localhost:" + puerto + uri;
	}

}
