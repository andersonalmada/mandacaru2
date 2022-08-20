package br.ufc.mandacaru5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mandacaru5.model.Product;
import br.ufc.mandacaru5.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public void save(int id, Product entity) {
		if (id != 0) {
			entity.setId(id);
		}

		productRepository.save(entity);
	}

	public void delete(int id) {
		Product product = find(id);
		productRepository.delete(product);
	}

	public Product find(int id) {
		if (id < 1) {
			return null;
		}

		Optional<Product> product = productRepository.findById(id);

		if (product.isPresent()) {
			return product.get();
		}

		return null;
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findByName(String str) {
		if (str.length() < 3) {
			return null;
		}

		return productRepository.findFirstByName(str);
	}

}
