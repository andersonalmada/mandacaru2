package br.ufc.mandacaru5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.mandacaru5.model.Feedback;
import br.ufc.mandacaru5.model.Product;
import br.ufc.mandacaru5.repository.FeedbackRepository;
import br.ufc.mandacaru5.repository.ProductRepository;

@Service
public class FeedbackService {

	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	ProductRepository productRepository;

	public void update(int id, Feedback entity) {
		Feedback feedback = find(id);		
		feedback.setMessage(entity.getMessage());
		
		feedbackRepository.save(feedback);				
	}
	
	public void save(int product_id, Feedback entity) {
		Product product = productRepository.findById(product_id).get();
		entity.setProduct(product);
		feedbackRepository.save(entity);				
	}

	public void delete(int id) {
		Feedback feedback = find(id);
		feedbackRepository.delete(feedback);
	}

	public Feedback find(int id) {
		if (id < 1) {
			return null;
		}

		Optional<Feedback> feedback = feedbackRepository.findById(id);

		if (feedback.isPresent()) {
			return feedback.get();
		}

		return null;
	}

	public List<Feedback> findAll(int product_id) {
		return feedbackRepository.findByProductId(product_id);
	}
}
