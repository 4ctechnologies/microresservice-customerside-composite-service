package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kaj on 29/09/15.
 */
@RestController
public class CustomerCompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerCompositeService.class);

    @Autowired
    CustomerCompositeIntegration customerIntegration;

    @RequestMapping(method = RequestMethod.GET, value="/customers")
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        return customerIntegration.getAllCustomers();
    }

    @RequestMapping(method = RequestMethod.GET, value="/customers/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        return customerIntegration.getCustomerById(customerId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return customerIntegration.createCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer, @RequestHeader HttpHeaders headers) {
        return customerIntegration.updateCustomer(customerId, customer, headers);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/customers/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String customerId, @RequestHeader HttpHeaders headers) {
        return customerIntegration.deleteCustomer(customerId, headers);
    }
}
