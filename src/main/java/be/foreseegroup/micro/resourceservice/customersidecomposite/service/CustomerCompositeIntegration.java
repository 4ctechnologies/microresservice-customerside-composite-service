package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Kaj on 29/09/15.
 */
@Component
public class CustomerCompositeIntegration {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerCompositeIntegration.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ServiceUtils util;

    /**
     * @todo:
     * Hystrix is also using the fallbackMethod when the person resource returns (intended) 4xx errors, e.g. 404 not found
     */

    @HystrixCommand(fallbackMethod = "customersFallback")
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        LOG.debug("Will call getAllCustomers with Hystrix protection");

        URI uri = util.getServiceUrl("customer");
        String url = uri.toString() + "/customers";
        LOG.debug("getAllCustomers from URL: {}", url);

        ParameterizedTypeReference<Iterable<Customer>> responseType = new ParameterizedTypeReference<Iterable<Customer>>() {};
        ResponseEntity<Iterable<Customer>> customers = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

        return customers;
    }

    @HystrixCommand(fallbackMethod = "customerFallback")
    public ResponseEntity<Customer> getCustomerById(String customerId) {
        LOG.debug("Will call getCustomerById with Hystrix protection");

        URI uri = util.getServiceUrl("customer");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("getCustomerById from URL: {}", url);

        ResponseEntity<Customer> customer = restTemplate.getForEntity(url, Customer.class);
        return customer;
    }

    @HystrixCommand(fallbackMethod = "customerFallback")
    public ResponseEntity<Customer> createCustomer(Customer customer) {
        LOG.debug("Will call createCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customer");
        String url = uri.toString() + "/customers";
        LOG.debug("createCustomer from URL: {}", url);

        ResponseEntity<Customer> resultCustomer = restTemplate.postForEntity(url, customer, Customer.class);
        return resultCustomer;
    }

    @HystrixCommand(fallbackMethod = "customerFallback")
    public ResponseEntity<Customer> updateCustomer(String customerId, Customer customer, HttpHeaders headers) {
        LOG.debug("Will call updateCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customer");
        String url = uri.toString() + "/customers/"+customerId;
        LOG.debug("updateUnit from URL: {}", url);

        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);
        ResponseEntity<Customer> resultCustomer = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Customer.class);
        return resultCustomer;
    }

    @HystrixCommand(fallbackMethod = "customerFallback")
    public ResponseEntity<Customer> deleteCustomer(String customerId, HttpHeaders headers) {
        LOG.debug("Will call deleteCustomer with Hystrix protection");

        URI uri = util.getServiceUrl("customer");
        String url = uri.toString() + "/customer/"+customerId;
        LOG.debug("deleteCustomer from URL: {}", url);

        HttpEntity<Customer> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Customer> resultCustomer = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Customer.class);
        return resultCustomer;
    }

    ResponseEntity<Customer> customerFallback(String identifier) {
        return customerFallback();
    }
    ResponseEntity<Customer> customerFallback(Customer customer) {
        return customerFallback();
    }
    ResponseEntity<Customer> customerFallback(String identifier, Customer customer, HttpHeaders headers) {
        return customerFallback();
    }
    ResponseEntity<Customer> customerFallback(String identifier, HttpHeaders headers) {
        return customerFallback();
    }

    ResponseEntity<Customer> customerFallback() {
        LOG.warn("Using fallback method for customer-service");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    ResponseEntity<Iterable<Customer>> customersFallback() {
        LOG.warn("Using fallback method for customer-service");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
