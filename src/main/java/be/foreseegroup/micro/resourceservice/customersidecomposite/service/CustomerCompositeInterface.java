package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Customer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Created by Kaj on 29/09/15.
 */
public interface CustomerCompositeInterface {
    ResponseEntity<Iterable<Customer>> getAllCustomers();
    ResponseEntity<Customer> getCustomerById(String customerId);
    ResponseEntity<Customer> createCustomer(Customer customer);
    ResponseEntity<Customer> updateCustomer(String customerId, Customer customer, HttpHeaders headers);
    ResponseEntity<Customer> deleteCustomer(String customerId, HttpHeaders headers);
}
