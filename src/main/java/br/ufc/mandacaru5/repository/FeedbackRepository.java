package br.ufc.mandacaru5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.mandacaru5.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	List<Feedback> findByProductId(int id);
}
