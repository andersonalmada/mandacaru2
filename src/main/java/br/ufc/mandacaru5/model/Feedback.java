package br.ufc.mandacaru5.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_generator")
    @SequenceGenerator(name="feedback_generator", sequenceName = "feedback_seq", allocationSize=1)
	private int id;
	private String message;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", message=" + message + "]";
	}

	public Feedback(int id, String message, Product product) {
		super();
		this.id = id;
		this.message = message;
		this.product = product;
	}

	public Feedback() {
		super();
	}

}
