package br.ufc.mandacaru5.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.ufc.mandacaru5.model.Feedback;
import br.ufc.mandacaru5.model.Product;
import br.ufc.mandacaru5.service.ProductService;

public class ProductControllerTest {
	
	// Product
	private static final int ID = 1;
	private static final String NAME = "notebook";
	private static final double PRICE = 450.0;

	// Feedback
	private static final int ID_F1 = 1;
	private static final int ID_F2 = 2;
	private static final String MESSAGE_F1 = "muito rapido";
	private static final String MESSAGE_F2 = "muito lento";

	@InjectMocks
	private ProductController controller;

	@Mock
	private ProductService service;

	private Product product;

	private List<Feedback> listFeedback;

    @Autowired
    private TestRestTemplate restTemplate;

	private void start() {
		Feedback feedback1 = new Feedback();
		feedback1.setId(ID_F1);
		feedback1.setMessage(MESSAGE_F1);

		Feedback feedback2 = new Feedback();
		feedback2.setId(ID_F2);
		feedback2.setMessage(MESSAGE_F2);

		listFeedback = new ArrayList<Feedback>();

		product = new Product(ID, NAME, PRICE, listFeedback);
	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	public void whenFindByIdThenReturnSuccess() {
		when(service.find(anyInt())).thenReturn(product);

		ResponseEntity<Product> response = controller.find(ID);

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Product.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(PRICE, response.getBody().getPrice());
		assertEquals(listFeedback, response.getBody().getFeedbacks());
	}

	@Test
	public void whenFindAllThenReturnSuccess() {
		when(service.findAll()).thenReturn(List.of(product));

		ResponseEntity<List<Product>> response = controller.findAll();

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ID, response.getBody().get(0).getId());
		assertEquals(NAME, response.getBody().get(0).getName());
		assertEquals(PRICE, response.getBody().get(0).getPrice());
		assertEquals(listFeedback, response.getBody().get(0).getFeedbacks());
	}

	@Test
	public void whenCreateThenReturnSuccess() {
		doNothing().when(service).save(0, product);

		controller.save(product);

		verify(service).save(0, product);
	}

	@Test
	public void whenUpdateThenReturnSuccess() {
		doNothing().when(service).save(ID, product);

		controller.update(ID, product);

		verify(service).save(ID, product);
	}

	@Test
	public void whenDeleteThenReturnSuccess() {
		doNothing().when(service).delete(ID);

		controller.delete(ID);

		verify(service).delete(ID);
	}

	@Test
	public void whenFindByNameThenReturnSuccess() {
		when(service.findByName(anyString())).thenReturn(product);

		ResponseEntity<Product> response = controller.findByName(NAME);

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Product.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(PRICE, response.getBody().getPrice());
		assertEquals(listFeedback, response.getBody().getFeedbacks());
	}

	@Test
	public void whenFindByNameThenReturnNotFound() {
		when(service.findByName(anyString())).thenReturn(null);

		ResponseEntity<Product> response = controller.findByName(NAME);

		assertNotNull(response);
		assertNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
