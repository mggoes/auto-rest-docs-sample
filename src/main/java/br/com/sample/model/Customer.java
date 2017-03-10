package br.com.sample.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Customer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	/**
	 * The customer's identification
	 */
	@NotNull
	private Integer id;
	/**
	 * The customer's name.
	 */
	@NotEmpty
	@Size(min = 10, max = 5000)
	private String name;
}
