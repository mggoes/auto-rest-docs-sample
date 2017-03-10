package br.com.sample.controller;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sample.model.Customer;

/**
 * The type Customer controller.
 */
@RestController
public class CustomerController {

	/**
	 * A `GET` request to list all customers.
	 *
	 * @return the list
	 */
	@GetMapping("/customers")
	public List<Customer> customers() {
		return asList(new Customer(1, "Customer 1"), new Customer(2, "Customer 2"), new Customer(3, "Customer 3"));
	}

}
