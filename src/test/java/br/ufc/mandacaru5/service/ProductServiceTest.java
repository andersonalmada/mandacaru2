package br.ufc.mandacaru5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ufc.mandacaru5.model.Feedback;
import br.ufc.mandacaru5.model.Product;
import br.ufc.mandacaru5.repository.ProductRepository;

public class ProductServiceTest {

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
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private Product product;

	private List<Feedback> listFeedback;

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
	public void whenFindByIdThenReturnAProduct() {
		when(repository.findById(anyInt())).thenReturn(Optional.of(product));

		Product response = service.find(ID);

		assertNotNull(response);
		assertEquals(Product.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(PRICE, response.getPrice());
		assertEquals(listFeedback, response.getFeedbacks());
	}

	@Test
	public void whenFindByIdThenReturnNullIfIdLessThan1() {
		Product response = service.find(0);
		assertNull(response);
	}

	@Test
	public void whenFindByIdThenReturnNullIfOptionalNotPresent() {
		when(repository.findById(anyInt())).thenReturn(Optional.empty());

		Product response = service.find(ID);
		assertNull(response);
	}

	@Test
	public void whenFindAllThenReturnAnList() {
		when(repository.findAll()).thenReturn(List.of(product));

		List<Product> response = service.findAll();

		assertNotNull(response);
		assertEquals(ID, response.get(0).getId());
		assertEquals(NAME, response.get(0).getName());
		assertEquals(PRICE, response.get(0).getPrice());
		assertEquals(listFeedback, response.get(0).getFeedbacks());
	}

	@Test
	public void whenSaveVerifySuccess() {
		when(repository.save(any())).thenReturn(product);

		service.save(0, product);

		verify(repository).save(any());
	}

	@Test
	public void whenUpdateVerifySuccess() {
		when(repository.save(any())).thenReturn(product);
		
		service.save(ID, product);

		verify(repository).save(any());
	}
	
	@Test
	public void whenDeleteVerifySuccess() {
		when(repository.findById(anyInt())).thenReturn(Optional.of(product));
		doNothing().when(repository).delete(product);
		
		service.delete(ID);

		verify(repository).findById(anyInt());
		verify(repository).delete(any());
	}
	
	@Test
	public void whenFindByNameThenReturnAProduct() {
		when(repository.findFirstByName(anyString())).thenReturn(product);

		Product response = service.findByName(NAME);
		
		assertNotNull(response);
		assertEquals(NAME, response.getName());
	}
	
	@Test
	public void whenFindByNameThenReturnNullIfNameLess3() {
		when(repository.findFirstByName(anyString())).thenReturn(null);

		Product response = service.findByName("as");
		
		assertNull(response);
	}
}
