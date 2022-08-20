package br.ufc.mandacaru5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.mandacaru5.model.Product;
import br.ufc.mandacaru5.service.ProductService;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

	@Autowired
	ProductService service;

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return new ResponseEntity<List<Product>>(service.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<Product> find(@PathVariable("id") int id) {
		return new ResponseEntity<Product>(service.find(id), HttpStatus.OK);
	}

	@GetMapping(path = "/search")
	public ResponseEntity<Product> findByName(@RequestParam("name") String name) {
		Product product = service.findByName(name);
		
		if(product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);	
		} else {
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public void save(@RequestBody Product product) {
		service.save(0, product);
	}

	@PutMapping(path = "{id}")
	public void update(@PathVariable("id") int id, @RequestBody Product product) {
		service.save(id, product);
	}

	@DeleteMapping(path = "{id}")
	public void delete(@PathVariable("id") int id) {
		service.delete(id);
	}
}
