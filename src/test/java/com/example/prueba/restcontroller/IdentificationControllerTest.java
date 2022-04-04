package com.example.prueba.restcontroller;

import static org.junit.jupiter.api.Assertions.*;


import java.util.HashMap;

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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IdentificationControllerTest {

	@Autowired
	private TestRestTemplate client;

	@LocalServerPort
	private int puerto;

	@Test
	@Order(1)
	void testAddIdentification() {
		Identification identification = new Identification("INE TEST", "INE DESCRIPTION TEST");

		ResponseEntity<Identification> respuesta = client.postForEntity(crearUri("/identification/"), identification,
				Identification.class);
		assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		Identification IdentificationCreated = respuesta.getBody();
		assertNotNull(IdentificationCreated);
		assertEquals("INE TEST", IdentificationCreated.getIdentificationName());
		assertEquals("INE DESCRIPTION TEST", IdentificationCreated.getDescription());
	}

	@Test
	@Order(2)
	void testUpdateIdentification() {

		ResponseEntity<Identification> respuesta = client.getForEntity(crearUri("/identification/3"),
				Identification.class);
		Identification identification = respuesta.getBody();
		identification.setIdentificationName("INE TEST UPDT");
		identification.setDescription("INE DESCRIPTION TEST UPDT");

		HttpEntity<Identification> identificationEntity = new HttpEntity<Identification>(identification);
		ResponseEntity<Identification> exchange = client.exchange(crearUri("/identification/3"), HttpMethod.PUT,
				identificationEntity, Identification.class);

		assertEquals(HttpStatus.OK, exchange.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, exchange.getHeaders().getContentType());
		assertNotNull(exchange.getBody());
		assertEquals("INE TEST UPDT", exchange.getBody().getIdentificationName());
		assertEquals("INE DESCRIPTION TEST UPDT", exchange.getBody().getDescription());
	}
	
	

	
	@Test
	@Order(3)
	void testGetIdentification() {
		ResponseEntity<Identification> respuesta = client.getForEntity(crearUri("/identification/3"), Identification.class);
		Identification identification = respuesta.getBody();
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		assertNotNull(identification);
		assertEquals(3L, identification.getIdIdentification());

	}
	
	@Test
	@Order(4)
	void testGetIdentificationNotExists() {
		ResponseEntity<Identification> respuesta = client.getForEntity(crearUri("/identification/100"), Identification.class);
		Identification identification = respuesta.getBody();
		assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
		assertNull(identification);

	}
	
	
	
	@Test
	@Order(5)
	void testGetAllIdentifications() {

		ResponseEntity<Identification[]> respuesta = client.getForEntity(crearUri("/identification/"), Identification[].class);
		assertEquals(HttpStatus.OK, respuesta.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());
		
	}

	@Test
	@Order(6)
	void testDeleteIdentification() {
		Map<String, Long> pathVariables = new HashMap<>();
		pathVariables.put("id", 3L);
		ResponseEntity<Identification> exchange = client.exchange(crearUri("/identification/{id}"), HttpMethod.DELETE, null,
				Identification.class, pathVariables);

		assertEquals(HttpStatus.OK, exchange.getStatusCode());
		assertTrue(exchange.hasBody());
		assertEquals(false, exchange.getBody().getActive());

	}
	private String crearUri(String uri) {
		return "http://localhost:" + puerto + uri;
	}

}
